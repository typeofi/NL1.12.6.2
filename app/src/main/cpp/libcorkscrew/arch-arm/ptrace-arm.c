/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#define LOG_TAG "Corkscrew"
//#define LOG_NDEBUG 0

#include <linux/elf.h>

#include "../ptrace-arch.h"

// //#include <sys/exec_elf.h>
//#include <cutils/log.h>

#ifndef PT_ARM_EXIDX
#define PT_ARM_EXIDX 0x70000001
#endif

static void load_exidx_header(pid_t pid, map_info_t *mi,
                              uintptr_t *out_exidx_start, size_t *out_exidx_size) {
    uint32_t Elf32_phoff;
    uint32_t Elf32_phentsize_ehsize;
    uint32_t Elf32_shentsize_phnum;
    if (try_get_word_ptrace(pid, mi->start + offsetof(Elf32_Ehdr, e_phoff), &Elf32_phoff)
        && try_get_word_ptrace(pid, mi->start + offsetof(Elf32_Ehdr, e_ehsize),
                               &Elf32_phentsize_ehsize)
        && try_get_word_ptrace(pid, mi->start + offsetof(Elf32_Ehdr, e_phnum),
                               &Elf32_shentsize_phnum)) {
        uint32_t Elf32_phentsize = Elf32_phentsize_ehsize >> 16;
        uint32_t Elf32_phnum = Elf32_shentsize_phnum & 0xffff;
        for (uint32_t i = 0; i < Elf32_phnum; i++) {
            uintptr_t Elf32_phdr = mi->start + Elf32_phoff + i * Elf32_phentsize;
            uint32_t Elf32_phdr_type;
            if (!try_get_word_ptrace(pid, Elf32_phdr + offsetof(Elf32_Phdr, p_type),
                                     &Elf32_phdr_type)) {
                break;
            }
            if (Elf32_phdr_type == PT_ARM_EXIDX) {
                uint32_t Elf32_phdr_offset;
                uint32_t Elf32_phdr_filesz;
                if (!try_get_word_ptrace(pid, Elf32_phdr + offsetof(Elf32_Phdr, p_offset),
                                         &Elf32_phdr_offset)
                    || !try_get_word_ptrace(pid, Elf32_phdr + offsetof(Elf32_Phdr, p_filesz),
                                            &Elf32_phdr_filesz)) {
                    break;
                }
                *out_exidx_start = mi->start + Elf32_phdr_offset;
                *out_exidx_size = Elf32_phdr_filesz / 8;
                //ALOGV("Parsed EXIDX header info for %s: start=0x%08x, size=%d", mi->name,
                //         *out_exidx_start, *out_exidx_size);
                return;
            }
        }
    }
    *out_exidx_start = 0;
    *out_exidx_size = 0;
}

void load_ptrace_map_info_data_arch(pid_t pid, map_info_t *mi, map_info_data_t *data) {
    load_exidx_header(pid, mi, &data->exidx_start, &data->exidx_size);
}

void free_ptrace_map_info_data_arch(map_info_t *mi, map_info_data_t *data) {
}
