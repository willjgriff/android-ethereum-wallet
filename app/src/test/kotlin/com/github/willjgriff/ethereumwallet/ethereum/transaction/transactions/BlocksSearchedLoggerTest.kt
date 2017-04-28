package com.github.willjgriff.ethereumwallet.ethereum.transaction.transactions

import com.github.willjgriff.ethereumwallet.ethereum.transactions.BlocksSearchedLogger
import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.BlockRange
import com.github.willjgriff.ethereumwallet.ethereum.transactions.storage.TransactionsStorage
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.shouldEqual
import org.junit.Test

/**
 * Created by williamgriffiths on 23/04/2017.
 */
class BlocksSearchedLoggerTest {

    val mockBlocksSearchedStorage = mock<TransactionsStorage> {
        on { getBlocksSearched() } doReturn listOf<BlockRange>()
    }

    val subject: BlocksSearchedLogger = BlocksSearchedLogger(mockBlocksSearchedStorage)

    @Test
    fun addingSearchedBlocksAreAddedToList() {
        subject.apply {
            blockSearched(10)
            blockSearched(9)
            blockSearched(8)
        }

        verify(mockBlocksSearchedStorage, atLeastOnce()).storeBlocksSearched(listOf(BlockRange(10, 8)))
    }

    @Test
    fun addingSeparatedSearchedBlocksCreatesMultipleRanges() {
        subject.apply {
            blockSearched(10)
            blockSearched(9)
            blockSearched(8)
            blockSearched(15)
            blockSearched(14)
            blockSearched(13)
        }
        verify(mockBlocksSearchedStorage, atLeastOnce()).storeBlocksSearched(listOf(BlockRange(10, 8), BlockRange(15, 13)))
    }

    @Test
    fun addingTheSameBlockManyTimesIncludesBlockRangeOnce() {
        subject.apply {
            blockSearched(10)
            blockSearched(10)
            blockSearched(10)
        }
        verify(mockBlocksSearchedStorage, atLeastOnce()).storeBlocksSearched(listOf(BlockRange(10, 10)))
    }

    @Test
    fun addingBlocksInAscendingOrderCreatesExpectedBlockRanges() {
        subject.apply {
            blockSearched(10)
            blockSearched(11)
            blockSearched(12)
        }
        verify(mockBlocksSearchedStorage, atLeastOnce()).storeBlocksSearched(listOf(BlockRange(10, 10), BlockRange(11, 11), BlockRange(12, 12)))
    }

    @Test
    fun addingBlocksInRandomOrderStillAddsToExpectedBlockRanges() {
        subject.apply {
            blockSearched(10)
            blockSearched(20)
            blockSearched(11)
            blockSearched(19)
            blockSearched(10)
            blockSearched(11)
        }
        verify(mockBlocksSearchedStorage, atLeastOnce()).storeBlocksSearched(listOf(BlockRange(10, 10), BlockRange(20, 19), BlockRange(11, 11)))
    }

//    @Test
//    fun addingBlocksSearchDoesntCreateLowerGreterThanUpper() {
//        subject.apply {
//            blockSearched(10)
//            blockSearched(12)
//        }
//        val expectedList = listOf(BlockRange(10, 10), BlockRange(12, 12))
//        verify(mockBlocksSearchedStorage, atLeastOnce()).storeBlocksSearched(expectedList)
//    }

    @Test
    fun searchBlocksAreCorrectWhenNoBlocksHaveBeenSearched() {
        val searchBlocks = subject.getBlocksToSearchFromTopBlock(15, 5)
        searchBlocks shouldEqual listOf(BlockRange(15, 11))
    }

    @Test
    fun searchBlocksAreCorrectWhenBlocksToSearchIsTheSameAsTopBlock() {
        val searchBlocks = subject.getBlocksToSearchFromTopBlock(5, 5)
        searchBlocks shouldEqual listOf(BlockRange(5, 1))
    }

    @Test
    fun searchBlocksAreCorrectWhenBlocksToSearchIsLargerThanTopBlock() {
        val searchBlocks = subject.getBlocksToSearchFromTopBlock(5, 6)
        searchBlocks shouldEqual listOf(BlockRange(5, 0))
    }

    @Test
    fun searchBlocksAreCorrectWhenBlocksToSearchIsLargerThanBlocksAvailable() {
        val searchBlocks = subject.getBlocksToSearchFromTopBlock(5, 7)
        searchBlocks shouldEqual listOf(BlockRange(5, 0))
    }

    @Test
    fun blocksSearchedArentInBlocksToSearchResponse() {
        subject.apply {
            blockSearched(10)
            blockSearched(9)
            blockSearched(8)
        }
        val searchBlocks = subject.getBlocksToSearchFromTopBlock(12, 5)
        searchBlocks shouldEqual listOf(BlockRange(12, 11), BlockRange(7, 5))
    }

    @Test
    fun multipleBlockRangesSearchedArentInBlocksToSearchReponse() {
        subject.apply {
            blockSearched(10)
            blockSearched(9)
            blockSearched(13)
            blockSearched(12)
        }
        val searchBlocks = subject.getBlocksToSearchFromTopBlock(15, 5)
        searchBlocks shouldEqual listOf(BlockRange(15, 14), BlockRange(11, 11), BlockRange(8, 7))

    }

    @Test
    fun manyMultipleBlockRangesSearchedArentInBlocksToSearchResponse() {
        subject.apply {
            blockSearched(10)
            blockSearched(9)
            blockSearched(12)
            blockSearched(15)
            blockSearched(14)
        }
        val searchBlocks = subject.getBlocksToSearchFromTopBlock(16, 5)
        searchBlocks shouldEqual listOf(BlockRange(16, 16), BlockRange(13, 13), BlockRange(11, 11), BlockRange(8, 7))
    }

    @Test
    fun manyMultipleBlockRangesSearchedArentInBlocksToSearchResponse2() {
        subject.apply {
            blockSearched(10)
            blockSearched(9)
            blockSearched(12)
            blockSearched(15)
            blockSearched(14)
            blockSearched(20)
            blockSearched(19)
            blockSearched(18)
        }
        val searchBlocks = subject.getBlocksToSearchFromTopBlock(23, 10)
        searchBlocks shouldEqual listOf(BlockRange(23, 21), BlockRange(17, 16), BlockRange(13, 13), BlockRange(11, 11), BlockRange(8, 6))
    }

    @Test
    fun returnsCorrectRangeWhenStartingFromBlockLowerThanInCurrentlySearchedRanges() {
        subject.apply {
            blockSearched(10)
            blockSearched(9)
            blockSearched(8)
        }
        val searchBlocks = subject.getBlocksToSearchFromTopBlock(6, 3)
        searchBlocks shouldEqual listOf(BlockRange(6, 4))
    }

    @Test
    fun doesntReturnNegativeBlockRangeWithMultipleRanges() {
        subject.apply {
            blockSearched(10)
            blockSearched(9)
            blockSearched(8)
            blockSearched(15)
            blockSearched(14)
        }
        val searchBlocks = subject.getBlocksToSearchFromTopBlock(9, 3)
        searchBlocks shouldEqual listOf(BlockRange(7, 5))
    }
}