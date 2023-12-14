package com.endcodev.myinvoice.domain.usecases

import com.endcodev.myinvoice.data.repository.ItemsRepository
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
    private lateinit var itemsRepository: ItemsRepository

    private lateinit var getSimpleItemsUseCase: GetSimpleItemsUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getSimpleItemsUseCase = GetSimpleItemsUseCase(itemsRepository)
    }

    @Test
    fun `invoke function should return null when itemId is null`() {
        val result = getSimpleItemsUseCase.invoke(null)

        assert(result == null)
    }

    @Test
    fun `invoke function should call getItemById when itemId is not null`() {
        val itemId = "123"
        val expectedItem = Product(iImage = null ,iCode = itemId, iName = "Test Item", iType = "type", iDescription = "description" )

        // Use MockK's every function to stub the method
        every { itemsRepository.getItemById(itemId) } returns expectedItem

        val result = getSimpleItemsUseCase.invoke(itemId)

        assert(result == expectedItem)
    }

    @Test
    fun `invoke function should return null when getItemById throws an exception`() {
        val itemId = "123"

        every { itemsRepository.getItemById(itemId) } throws Exception("Test Exception")

        val result = getSimpleItemsUseCase.invoke(itemId)

        assert(result == null)
    }
}