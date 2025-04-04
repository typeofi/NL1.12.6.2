


#include "wp/FactorizationRenderer.h"

void MSHookFunction(void *symbol, void *replace, void **result);

static int (*BlockTessellator_canRender)(int, int);

static int BlockTessellator$canRender(int a1, int a2) {
//    if ( FactorizationRenderer::getRenderByID((FactorizationRenderer *)FzRenderer, a2, 0) )
//        return 1;
//    else
    return BlockTessellator_canRender(a1, a2);
}


void test() {
//    MSHookFunction(&BlockTessellator::canRender, BlockTessellator$canRender, (void **) &BlockTessellator_canRender);
}