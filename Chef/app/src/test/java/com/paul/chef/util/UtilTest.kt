package com.paul.chef.util

import com.paul.chef.ChefApplication
import org.junit.Assert.*
import org.junit.Test
import kotlin.properties.Delegates

class UtilTest{
    @Test
    fun getPrice_test() {
        val expected = "NT$1,400"  //預期結果
        val actual = Util.getPerPrice(1400)
        assertEquals(expected, actual)
    }
}