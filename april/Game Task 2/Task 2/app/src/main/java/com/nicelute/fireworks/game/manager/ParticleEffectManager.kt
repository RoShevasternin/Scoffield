package com.nicelute.fireworks.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.g2d.ParticleEffect

class ParticleEffectManager(var assetManager: AssetManager) {

    private val params         = ParticleEffectLoader.ParticleEffectParameter()
    private val particleLoader = ParticleEffectLoader(InternalFileHandleResolver())

    var loadableParticleEffectList = mutableListOf<ParticleEffectData>()

    fun load() {
        assetManager.setLoader(ParticleEffect::class.java, ".p", particleLoader)
        loadableParticleEffectList.onEach { assetManager.load(it.path, ParticleEffect::class.java, params.apply {
            //atlasFile = "particle/particle.atlas"
        }) }
    }

    fun init() {
        loadableParticleEffectList.onEach { it.effect = assetManager[it.path, ParticleEffect::class.java] }
        loadableParticleEffectList.clear()
    }

    enum class EnumParticleEffect(val data: ParticleEffectData) {
        F1_SALUT_1(ParticleEffectData("particle/f1/salut1.p")),
        F1_SALUT_2(ParticleEffectData("particle/f1/salut2.p")),

        F2_SALUT_1(ParticleEffectData("particle/f2/salut1.p")),
        F2_SALUT_2(ParticleEffectData("particle/f2/salut2.p")),

        F3_SALUT_1(ParticleEffectData("particle/f3/salut1.p")),

        F4_SALUT_1(ParticleEffectData("particle/f4/salut1.p")),

        F5_SALUT_1(ParticleEffectData("particle/f5/salut1.p")),
        F5_SALUT_2(ParticleEffectData("particle/f5/salut2.p")),

        F6_SALUT_1(ParticleEffectData("particle/f6/salut1.p")),
        F6_SALUT_2(ParticleEffectData("particle/f6/salut2.p")),
    }

    data class ParticleEffectData(val path: String) {
        lateinit var effect: ParticleEffect
    }

}