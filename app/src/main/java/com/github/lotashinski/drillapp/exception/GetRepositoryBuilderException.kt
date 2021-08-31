package com.github.lotashinski.drillapp.exception

import java.lang.Exception

class GetRepositoryBuilderException(message: String, exception: Exception? = null) :
    Exception(message, exception)