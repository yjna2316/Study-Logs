package com.kopring.studylogs.monitoring.failureanalyzer

class UrlNotAccessibleException(val url: String,  cause: Throwable? = null): RuntimeException("URL $url is not accessible", cause)
