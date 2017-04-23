package com.github.willjgriff.ethereumwallet.ethereum.transaction.transactions

import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.BlockRange
import com.github.willjgriff.ethereumwallet.ethereum.transactions.BlocksSearchedLogger
import org.amshove.kluent.shouldEqual
import org.junit.Test

/**
 * Created by williamgriffiths on 23/04/2017.
 */
class BlocksSearchedLoggerTest {

    val subject: BlocksSearchedLogger = BlocksSearchedLogger()

    @Test
    fun addingSearchedBlocksAreAddedToList() {
        subject.apply {
            blockSearched(10)
            blockSearched(9)
            blockSearched(8)
        }

        subject.blockRangesSearched shouldEqual listOf(BlockRange(10, 8))
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

        subject.blockRangesSearched shouldEqual listOf(BlockRange(10, 8), BlockRange(15, 13))
    }

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
}