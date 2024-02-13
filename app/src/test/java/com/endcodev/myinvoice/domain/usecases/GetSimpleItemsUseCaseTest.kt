package com.endcodev.myinvoice.domain.usecases

import com.endcodev.myinvoice.data.repository.ProductsRepository
import com.endcodev.myinvoice.domain.models.product.Product
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test

/**
 * Test for [GetSimpleItemsUseCase].
 */
class GetSimpleItemsUseCaseTest {
    @RelaxedMockK
    private lateinit var productsRepository: ProductsRepository

    private lateinit var getSimpleItemsUseCase: GetSimpleItemsUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getSimpleItemsUseCase = GetSimpleItemsUseCase(productsRepository)
    }

    @Test
    fun `invoke function should return null when itemId is null`() {
        val result = getSimpleItemsUseCase.invoke(null)

        assert(result == null)
    }

    @Test
    fun `invoke function should call getItemById when itemId is not null`() {
        val itemId = "123"
        val expectedItem = Product(
            image = null,
            id = itemId,
            name = "Test Item",
            type = "type",
            description = "description",
            price = 10.0f,
            stock = 21.0f,
            cost = 10.0f,
        )

        // Use MockK's every function to stub the method
        every { productsRepository.getItemById(itemId) } returns expectedItem

        val result = getSimpleItemsUseCase.invoke(itemId)

        assert(result == expectedItem)
    }

    @Test
    fun `invoke function should return null when getItemById throws an exception`() {
        val itemId = "123"

        every { productsRepository.getItemById(itemId) } throws Exception("Test Exception")

        val result = getSimpleItemsUseCase.invoke(itemId)

        assert(result == null)
    }
}