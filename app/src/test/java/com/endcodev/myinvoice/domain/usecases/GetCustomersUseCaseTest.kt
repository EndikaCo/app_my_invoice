package com.endcodev.myinvoice.domain.usecases

import com.endcodev.myinvoice.data.repository.CustomersRepository
import com.endcodev.myinvoice.domain.models.customer.Customer
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

/**
 * Test for [GetCustomersUseCase].
 */
class GetCustomersUseCaseTest {

    @RelaxedMockK
    private lateinit var mockRepository: CustomersRepository

    private lateinit var getCustomersUseCase: GetCustomersUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getCustomersUseCase = GetCustomersUseCase(mockRepository)
    }

    @Test
    fun `test invoke`() = runBlocking {

        //Given
        val expectedCustomers = listOf(
            Customer(null, "Toyota Motor Corporation", "KDJ9576852", "+81 623213276", "Japan"),
            Customer(null, "Alibaba Group Holding Limited", "08876623VT", "+86 732132133", "China"),
            Customer(null, "Vodafone Group plc", "HJ3145125212", "+44 624223213", "United Kingdom"),
            Customer(
                null,
                "Samsung Electronics Co., Ltd.",
                "HSG23145112F",
                "+82 624223213",
                "South Korea"
            )
        )
        coEvery { mockRepository.getAllCustomersFromDB() } returns expectedCustomers

        // Act
        val result = getCustomersUseCase()

        //then
        assertEquals(expectedCustomers, result)
        coVerify(exactly = 1) { mockRepository.getAllCustomersFromDB() }
    }
}