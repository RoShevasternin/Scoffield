package com.plinko.ballwinx100.tool

import com.plinko.ballwinx100.databinding.ActivityMainBinding
import com.plinko.ballwinx100.util.Lottie


class LoaderStatus(
    private val lottie: Lottie,
) {

    constructor(
        binding: ActivityMainBinding
    ) : this(
        Lottie(binding)
    )

    fun hide() = lottie.hideLoader()

    fun showLoader() = lottie.showLoader()
}

