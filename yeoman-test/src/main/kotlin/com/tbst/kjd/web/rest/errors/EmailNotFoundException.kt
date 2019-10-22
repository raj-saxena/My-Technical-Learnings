package com.tbst.kjd.web.rest.errors

import org.zalando.problem.AbstractThrowableProblem
import org.zalando.problem.Exceptional
import org.zalando.problem.Status

class EmailNotFoundException :
    AbstractThrowableProblem(EMAIL_NOT_FOUND_TYPE, "Email address not registered", Status.BAD_REQUEST) {

    override fun getCause(): Exceptional? = super.cause

    companion object {
        private const val serialVersionUID = 1L
    }
}
