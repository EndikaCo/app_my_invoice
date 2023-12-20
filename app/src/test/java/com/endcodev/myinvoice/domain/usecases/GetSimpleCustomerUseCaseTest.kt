package com.endcodev.myinvoice.domain.usecases

import com.endcodev.myinvoice.data.database.entities.CustomersEntity
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
 * Test for [GetSimpleCustomerUseCase].
 */
class GetSimpleCustomerUseCaseTest {

    @RelaxedMockK
    private lateinit var mockRepository: CustomersRepository

    private lateinit var getSimpleCustomerUseCase: GetSimpleCustomerUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getSimpleCustomerUseCase = GetSimpleCustomerUseCase(mockRepository)
    }

    @Test
    fun `test invoke with valid customerIdentifier`() = runBlocking {

        //Given
        val customerIdentifier = "1"
        val expectedCustomer = Customer(null, "1", "name")
        coEvery { mockRepository.getCustomerById(customerIdentifier) } returns expectedCustomer

        // Act
        val result = getSimpleCustomerUseCase(customerIdentifier)

        //then
        assertEquals(expectedCustomer, result)
        coVerify(exactly = 1) { mockRepository.getCustomerById(customerIdentifier) }
    }

    @Test
    fun `test saveCustomer`() = runBlocking {

        //Given
        val customer = CustomersEntity("1", null, "name", "694", "Spain")

        // Act
        getSimpleCustomerUseCase.saveCustomer(customer)

        //then
        coVerify(exactly = 1) { mockRepository.insertCustomer(customer) }
    }

    @Test
    fun `test deleteCustomer`() {

        //Given
        val customerId = "1"

        // Act
        getSimpleCustomerUseCase.deleteCustomer(customerId)

        //then
        coVerify(exactly = 1) { mockRepository.deleteCustomer(customerId) }
    }
}
