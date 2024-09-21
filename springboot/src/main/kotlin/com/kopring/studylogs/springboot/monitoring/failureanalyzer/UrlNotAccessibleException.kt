package com.kopring.studylogs.springboot.monitoring.failureanalyzer

class UrlNotAccessibleException(val url: String,  cause: Throwable? = null): RuntimeException("URL $url is not accessible", cause)
