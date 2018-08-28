/*
 * Copyright 2015 Google Inc.
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

package com.example.library.anim

import android.animation.Animator
import android.animation.TimeInterpolator
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.transition.Transition
import android.util.ArrayMap
import android.util.Property
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator

import java.util.ArrayList

/**
 * Utility methods for working with animations.
 */
object AnimUtils {

    private var fastOutSlowIn: Interpolator? = null
    private var fastOutLinearIn: Interpolator? = null
    private var linearOutSlowIn: Interpolator? = null

    fun getFastOutSlowInInterpolator(context: Context): Interpolator? {
        if (fastOutSlowIn == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fastOutSlowIn = AnimationUtils.loadInterpolator(context,
                        android.R.interpolator.fast_out_slow_in)
            }
        }
        return fastOutSlowIn
    }

    fun getFastOutLinearInInterpolator(context: Context): Interpolator? {
        if (fastOutLinearIn == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fastOutLinearIn = AnimationUtils.loadInterpolator(context,
                        android.R.interpolator.fast_out_linear_in)
            }
        }
        return fastOutLinearIn
    }

    fun getLinearOutSlowInInterpolator(context: Context): Interpolator? {
        if (linearOutSlowIn == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                linearOutSlowIn = AnimationUtils.loadInterpolator(context,
                        android.R.interpolator.linear_out_slow_in)
            }
        }
        return linearOutSlowIn
    }

    /**
     * Linear interpolate between a and b with parameter t.
     */
    fun lerp(a: Float, b: Float, t: Float): Float {
        return a + (b - a) * t
    }


    /**
     * An implementation of [Property] to be used specifically with fields of
     * type
     * `float`. This type-specific subclass enables performance benefit by allowing
     * calls to a [set()][.set] function that takes the primitive
     * `float` type and avoids autoboxing and other overhead associated with the
     * `Float` class.
     *
     * @param <T> The class on which the Property is declared.
    </T> */
    abstract class FloatProperty<T>(name: String) : Property<T, Float>(Float::class.java, name) {

        /**
         * A type-specific override of the [.set] that is faster when dealing
         * with fields of type `float`.
         */
        abstract fun setValue(`object`: T, value: Float)

        override fun set(`object`: T, value: Float?) {
            setValue(`object`, value!!)
        }
    }

    /**
     * An implementation of [Property] to be used specifically with fields of
     * type
     * `int`. This type-specific subclass enables performance benefit by allowing
     * calls to a [set()][.set] function that takes the primitive
     * `int` type and avoids autoboxing and other overhead associated with the
     * `Integer` class.
     *
     * @param <T> The class on which the Property is declared.
    </T> */
    abstract class IntProperty<T>(name: String) : Property<T, Int>(Int::class.java, name) {

        /**
         * A type-specific override of the [.set] that is faster when dealing
         * with fields of type `int`.
         */
        abstract fun setValue(`object`: T, value: Int)

        override fun set(`object`: T, value: Int) {
            setValue(`object`, value)
        }

    }

    /**
     * https://halfthought.wordpress.com/2014/11/07/reveal-transition/
     *
     *
     * Interrupting Activity transitions can yield an OperationNotSupportedException when the
     * transition tries to pause the animator. Yikes! We can fix this by wrapping the Animator:
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    class NoPauseAnimator(private val mAnimator: Animator) : Animator() {
        private val mListeners = ArrayMap<Animator.AnimatorListener, Animator.AnimatorListener>()

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        override fun addListener(listener: Animator.AnimatorListener) {
            val wrapper = AnimatorListenerWrapper(this, listener)
            if (!mListeners.containsKey(listener)) {
                mListeners[listener] = wrapper
                mAnimator.addListener(wrapper)
            }
        }

        override fun cancel() {
            mAnimator.cancel()
        }

        override fun end() {
            mAnimator.end()
        }

        override fun getDuration(): Long {
            return mAnimator.duration
        }

        override fun getInterpolator(): TimeInterpolator {
            return mAnimator.interpolator
        }

        override fun setInterpolator(timeInterpolator: TimeInterpolator) {
            mAnimator.interpolator = timeInterpolator
        }

        override fun getListeners(): ArrayList<Animator.AnimatorListener> {
            return ArrayList(mListeners.keys)
        }

        override fun getStartDelay(): Long {
            return mAnimator.startDelay
        }

        override fun setStartDelay(delayMS: Long) {
            mAnimator.startDelay = delayMS
        }

        override fun isPaused(): Boolean {
            return mAnimator.isPaused
        }

        override fun isRunning(): Boolean {
            return mAnimator.isRunning
        }

        override fun isStarted(): Boolean {
            return mAnimator.isStarted
        }

        /* We don't want to override pause or resume methods because we don't want them
         * to affect mAnimator.
        public void pause();

        public void resume();

        public void addPauseListener(AnimatorPauseListener listener);

        public void removePauseListener(AnimatorPauseListener listener);
        */

        override fun removeAllListeners() {
            mListeners.clear()
            mAnimator.removeAllListeners()
        }

        override fun removeListener(listener: Animator.AnimatorListener) {
            val wrapper = mListeners[listener]
            if (wrapper != null) {
                mListeners.remove(listener)
                mAnimator.removeListener(wrapper)
            }
        }

        override fun setDuration(durationMS: Long): Animator {
            mAnimator.duration = durationMS
            return this
        }

        override fun setTarget(target: Any?) {
            mAnimator.setTarget(target)
        }

        override fun setupEndValues() {
            mAnimator.setupEndValues()
        }

        override fun setupStartValues() {
            mAnimator.setupStartValues()
        }

        override fun start() {
            mAnimator.start()
        }
    }

    internal class AnimatorListenerWrapper(private val mAnimator: Animator, private val mListener: Animator.AnimatorListener) : Animator.AnimatorListener {

        override fun onAnimationStart(animator: Animator) {
            mListener.onAnimationStart(mAnimator)
        }

        override fun onAnimationEnd(animator: Animator) {
            mListener.onAnimationEnd(mAnimator)
        }

        override fun onAnimationCancel(animator: Animator) {
            mListener.onAnimationCancel(mAnimator)
        }

        override fun onAnimationRepeat(animator: Animator) {
            mListener.onAnimationRepeat(mAnimator)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    class TransitionListenerAdapter : Transition.TransitionListener {

        override fun onTransitionStart(transition: Transition) {

        }

        override fun onTransitionEnd(transition: Transition) {

        }

        override fun onTransitionCancel(transition: Transition) {

        }

        override fun onTransitionPause(transition: Transition) {

        }

        override fun onTransitionResume(transition: Transition) {

        }
    }

}
