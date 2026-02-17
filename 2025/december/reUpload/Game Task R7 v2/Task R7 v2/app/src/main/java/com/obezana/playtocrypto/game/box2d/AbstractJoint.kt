package com.obezana.playtocrypto.game.box2d

import com.badlogic.gdx.physics.box2d.Joint
import com.badlogic.gdx.physics.box2d.JointDef
import com.obezana.playtocrypto.game.utils.advanced.AdvancedBox2dScreen
import com.obezana.playtocrypto.util.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AbstractJoint<T : Joint>(val screenBox2d  : AdvancedBox2dScreen) {

    var coroutine: CoroutineScope? = null
        private set

    var joint: T? = null
        private set


    fun destroy() {
        if (joint != null) {
            cancelCoroutinesAll(coroutine)

            with(screenBox2d.worldUtil) {
                world.destroyJoint(joint)
                joint = null
            }
        }
    }

    fun create(jointDef: JointDef) {
        joint = screenBox2d.worldUtil.world.createJoint(jointDef).apply { userData = this@AbstractJoint } as T
        coroutine = CoroutineScope(Dispatchers.Default)
    }

    fun startDestroy(time: Long = 0) {
        coroutine?.launch {
            delay(time)
            screenBox2d.worldUtil.destroyJointList.add(this@AbstractJoint)
        }
    }


}