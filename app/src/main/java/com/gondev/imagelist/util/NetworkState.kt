package com.gondev.imagelist.util

import java.io.PrintWriter
import java.io.StringWriter

/**
 * 네트워크 상태를 나타냅니다
 */
sealed class NetworkState<T>(val data:T?) {
	class Loading<T>(data:T?) : NetworkState<T>(data)

	class Success<T>(data:T) : NetworkState<T>(data)

	class Error<T>(data:T?, val throwable: Throwable) : NetworkState<T>(data){
		fun getStackTrace(): String {
			val stringWriter = StringWriter()
			throwable.printStackTrace(PrintWriter(stringWriter))
			return stringWriter.toString()
		}
	}

	companion object{
		fun <T> loading(data: T?) = Loading(data)

		fun <T> success(data:T) = Success(data)

		fun <T> error(data:T?, throwable: Throwable) = Error(data, throwable)
	}
}
