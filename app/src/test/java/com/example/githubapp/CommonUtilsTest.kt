package com.example.githubapp

import com.example.githubapp.utils.CommonUtils
import com.google.common.truth.Truth.assertThat
import org.junit.Test

public class CommonUtilsTest {

    @Test
    fun formatDate_mayInput_expectedOutputReturned() {
        val result: String = CommonUtils.formatDate("2022-05-25T11:27:16Z")
        assertThat(result).isEqualTo("25-May-2022")
    }

    @Test
    fun formatDate_januaryInput_expectedOutputReturned() {
        val result: String = CommonUtils.formatDate("2022-01-05T11:27:04Z")
        assertThat(result).isEqualTo("05-Jan-2022")
    }
}