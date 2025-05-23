#include "jni.h"
#include <cstdlib>
#include <cstring>
#include <string>
#include <vector>
#include <map>
#include <dlfcn.h>
#include <android/log.h>

#include "mcpelauncher.h"

#define cppbool bool

#include "modscript_renderer_jni.h"
#include "modscript_shared.h"
#include "mcpe/mce/textureptr.h"

// search for HumanoidMobRenderer::HumanoidMobRenderer
#define MOBRENDERER_SIZE 204
// ModelPart::addBox
#define MODELPART_CUBEVECTOR_OFFSET 28
// ModelPart destructor
#define MODELPART_MESHBUFFER_OFFSET 76


extern "C" {
#define DLSYM_DEBUG
#ifdef DLSYM_DEBUG

void *debug_dlsym(void *handle, const char *symbol);

#define dlsym debug_dlsym
#endif //DLSYM_DEBUG

void **bl_EntityRenderDispatcher_instance;

EntityRenderer *(*bl_EntityRenderDispatcher_getRenderer)(void *, int);

static void (*bl_Mesh_reset)(void *);

static void
(*bl_HumanoidMobRenderer_HumanoidMobRenderer)(MobRenderer *, std::unique_ptr<HumanoidModel>,
                                              std::unique_ptr<HumanoidModel>,
                                              std::unique_ptr<HumanoidModel>, mce::TexturePtr,
                                              float);
static void (*bl_ModelPart_reset)(ModelPart *);

static std::vector<EntityRenderer *> bl_entityRenderers;

static std::map<long long, int> bl_renderTypeMap;

static EntityRenderer *(*bl_EntityRenderDispatcher_getRenderer_real)(void *, Entity *);

static ModelPart *bl_renderManager_getModelPart_impl(int rendererId, const char *modelPartName,
                                                     HumanoidModel **modelPtr) {
    MobRenderer *renderer;
    if (rendererId < 0x1000) {
        renderer = (MobRenderer *) bl_EntityRenderDispatcher_getRenderer(
                *bl_EntityRenderDispatcher_instance, rendererId);
    } else {
        renderer = (MobRenderer *) bl_entityRenderers[rendererId - 0x1000];
    }
    HumanoidModel *model = (HumanoidModel *) renderer->model; //TODO: make sure that this is indeed a humanoid model
    *modelPtr = model;
    if (strcmp(modelPartName, "head") == 0) {
        return &model->bipedHead;
    } else if (strcmp(modelPartName, "headwear") == 0) {
        return &model->bipedHeadwear;
    } else if (strcmp(modelPartName, "body") == 0) {
        return &model->bipedBody;
    } else if (strcmp(modelPartName, "rightArm") == 0) {
        return &model->bipedRightArm;
    } else if (strcmp(modelPartName, "leftArm") == 0) {
        return &model->bipedLeftArm;
    } else if (strcmp(modelPartName, "rightLeg") == 0) {
        return &model->bipedRightLeg;
    } else if (strcmp(modelPartName, "leftLeg") == 0) {
        return &model->bipedLeftLeg;
    } else {
        return NULL;
    }
    return NULL;
}

ModelPart *bl_renderManager_getModelPart(int rendererId, const char *modelPartName) {
    HumanoidModel *modelPtr = nullptr;
    ModelPart *retval = bl_renderManager_getModelPart_impl(rendererId, modelPartName, &modelPtr);
    /*
    if (retval && !retval->model) {
        __android_log_print(ANDROID_LOG_ERROR, "BlockLauncher", "Renderer id %d part %s doesn't have a model\n",
            rendererId, modelPartName);
        retval->model = modelPtr;
    }
    */
    return retval;
}

void bl_renderManager_invalidateModelPart(ModelPart *part) {
    void *meshBuffer = (void *) ((uintptr_t) part + MODELPART_MESHBUFFER_OFFSET);
    bl_Mesh_reset(meshBuffer);
}

int bl_renderManager_addRenderer(EntityRenderer *renderer) {
    bl_entityRenderers.push_back(renderer);
    int rendererId = 0x1000 + (bl_entityRenderers.size() - 1);
    return rendererId;
}

int bl_renderManager_createHumanoidRenderer() {
    HumanoidModel *model = new HumanoidModel(0, 0, 64, 64);

    HumanoidModel *model2 = new HumanoidModel(0, 0, 64, 64);

    HumanoidModel *model3 = new HumanoidModel(0, 0, 64, 64);


    MobRenderer *renderer = (MobRenderer *) operator new(MOBRENDERER_SIZE);
    bl_HumanoidMobRenderer_HumanoidMobRenderer(renderer, std::unique_ptr<HumanoidModel>(model),
                                               std::unique_ptr<HumanoidModel>(model2),
                                               std::unique_ptr<HumanoidModel>(model3),
                                               mce::TexturePtr(bl_minecraft->getTextures(),
                                                               "mob/steve.png"), 0);

    int retval = bl_renderManager_addRenderer((EntityRenderer *) renderer);
    return retval;
}

EntityRenderer *bl_EntityRenderDispatcher_getRenderer_hook(void *dispatcher, Entity *entity) {
    long long entityId = entity->getUniqueID();
    if (bl_renderTypeMap.count(entityId) != 0) {
        return bl_entityRenderers[bl_renderTypeMap[entityId] - 0x1000];
    }
    return bl_EntityRenderDispatcher_getRenderer_real(dispatcher, entity);
}

//static void* getMCPERenderType(int renderType) {
//	return bl_EntityRenderDispatcher_getRenderer_EntityRenderId(*bl_EntityRenderDispatcher_instance, renderType);
//}

bool bl_renderManager_setRenderType(Entity *entity, int renderType) {
    long long entityId = entity->getUniqueID();
    if (renderType >= 0x1000) {
        if ((renderType - 0x1000) >= bl_entityRenderers.size()) {
            __android_log_print(ANDROID_LOG_ERROR, "BlockLauncher",
                                "Renderer id %d is over size of %d",
                                renderType, bl_entityRenderers.size());
            return false;
        }
        bl_renderTypeMap[entityId] = renderType;
    } else {
        //if (!getMCPERenderType(renderType)) return false;
        bl_renderTypeMap.erase(entityId);
        entity->renderType = renderType;
    }
    return true;
}

int bl_renderManager_getRenderType(Entity *entity) {
    long long entityId = entity->getUniqueID();
    if (bl_renderTypeMap.count(entityId) != 0) {
        return bl_renderTypeMap[entityId];
    }
    return entity->renderType;
}

void bl_renderManager_clearRenderTypes() {
    bl_renderTypeMap.clear();
}

JNIEXPORT void JNICALL
Java_net_zhuoweizhang_mcpelauncher_api_modpe_RendererManager_nativeModelAddBox
        (JNIEnv *env, jclass clazz, jint rendererId, jstring modelPartName, jfloat xOffset,
         jfloat yOffset, jfloat zOffset,
         jint width, jint height, jint depth, jfloat scale, jint textureX, jint textureY,
         jboolean transparent,
         jfloat textureWidth, jfloat textureHeight) {
    const char *utfChars = env->GetStringUTFChars(modelPartName, NULL);
    HumanoidModel *model = nullptr;
    ModelPart *part = bl_renderManager_getModelPart_impl(rendererId, utfChars, &model);
    part->textureOffsetX = textureX;
    part->textureOffsetY = textureY;
    //part->transparent = transparent;
    if (transparent) part->material = &(model->materialAlphaTest);
    if (textureWidth > 0) part->textureWidth = textureWidth;
    if (textureHeight > 0) part->textureHeight = textureHeight;
    part->addBox(Vec3(xOffset, yOffset, zOffset), Vec3(width, height, depth), scale);
    bl_renderManager_invalidateModelPart(part);
    env->ReleaseStringUTFChars(modelPartName, utfChars);
}

JNIEXPORT void JNICALL Java_net_zhuoweizhang_mcpelauncher_api_modpe_RendererManager_nativeModelClear
        (JNIEnv *env, jclass clazz, jint rendererId, jstring modelPartName) {
    const char *utfChars = env->GetStringUTFChars(modelPartName, NULL);
    ModelPart *part = bl_renderManager_getModelPart(rendererId, utfChars);
    std::vector<Cube *> *cubeVector = (std::vector<Cube *> *) ((uintptr_t) part +
                                                               MODELPART_CUBEVECTOR_OFFSET);
    cubeVector->clear();
    bl_renderManager_invalidateModelPart(part);
    env->ReleaseStringUTFChars(modelPartName, utfChars);
}

JNIEXPORT jboolean JNICALL
Java_net_zhuoweizhang_mcpelauncher_api_modpe_RendererManager_nativeModelPartExists
        (JNIEnv *env, jclass clazz, jint rendererId, jstring modelPartName) {
    jboolean exists;
    const char *utfChars = env->GetStringUTFChars(modelPartName, NULL);
    ModelPart *part = bl_renderManager_getModelPart(rendererId, utfChars);
    exists = part != NULL;
    env->ReleaseStringUTFChars(modelPartName, utfChars);
    return exists;
}

JNIEXPORT jint JNICALL
Java_net_zhuoweizhang_mcpelauncher_api_modpe_RendererManager_nativeCreateHumanoidRenderer
        (JNIEnv *env, jclass clazz) {
    return bl_renderManager_createHumanoidRenderer();
}

JNIEXPORT void JNICALL
Java_net_zhuoweizhang_mcpelauncher_api_modpe_RendererManager_nativeModelSetRotationPoint
        (JNIEnv *env, jclass clazz, jint rendererId, jstring modelPartName, jfloat x, jfloat y,
         jfloat z) {
    const char *utfChars = env->GetStringUTFChars(modelPartName, NULL);
    ModelPart *part = bl_renderManager_getModelPart(rendererId, utfChars);
    part->offsetX = x;
    part->offsetY = y;
    part->offsetZ = z;
    bl_renderManager_invalidateModelPart(part);
    env->ReleaseStringUTFChars(modelPartName, utfChars);
}

void bl_renderManager_init(void *mcpelibhandle) {
    bl_EntityRenderDispatcher_getRenderer = (EntityRenderer *(*)(void *, int))
            dlsym(mcpelibhandle, "_ZN22EntityRenderDispatcher11getRendererE16EntityRendererId");
    bl_EntityRenderDispatcher_instance = (void **)
            dlsym(mcpelibhandle, "_ZN22EntityRenderDispatcher8instanceE");
    bl_Mesh_reset = (void (*)(void *))
            dlsym(mcpelibhandle, "_ZN3mce4Mesh5resetEv");
    bl_HumanoidMobRenderer_HumanoidMobRenderer = (void (*)(MobRenderer *,
                                                           std::unique_ptr<HumanoidModel>,
                                                           std::unique_ptr<HumanoidModel>,
                                                           std::unique_ptr<HumanoidModel>,
                                                           mce::TexturePtr, float))
            dlsym(mcpelibhandle,
                  "_ZN19HumanoidMobRendererC1ESt10unique_ptrI13HumanoidModelSt14default_deleteIS1_EES4_S4_N3mce10TexturePtrEf");
    void *getRenderer = dlsym(mcpelibhandle, "_ZN22EntityRenderDispatcher11getRendererER6Entity");
    mcpelauncher_hook(getRenderer, (void *) bl_EntityRenderDispatcher_getRenderer_hook,
                      (void **) &bl_EntityRenderDispatcher_getRenderer_real);
    bl_ModelPart_reset = (void (*)(ModelPart *))
            dlsym(mcpelibhandle, "_ZN9ModelPart5resetEv");
}

} //extern "C"
