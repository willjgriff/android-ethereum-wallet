package com.github.willjgriff.ethereumwallet.ethereum.transactions

import com.github.willjgriff.ethereumwallet.ethereum.transactions.model.BlockRange
import com.github.willjgriff.ethereumwallet.ethereum.transactions.storage.TransactionsStorage
import timber.log.Timber

/**
 * Created by williamgriffiths on 23/04/2017.
 */
class BlocksSearchedLogger(private val transactionsStorage: TransactionsStorage) {

    val EXCLUSIVE_RANGE_OFFSET = 1
    private val blockRangesSearched: MutableList<BlockRange> = transactionsStorage.getBlocksSearched().toMutableList()

    fun blockSearched(blockNumber: Long) {
        val blockRangeBlockNumberBelongsTo = blockRangesSearched
                .filter { it.lowerBlock == blockNumber + 1 || it.upperBlock == blockNumber }

        when (blockRangeBlockNumberBelongsTo.size) {
            0 -> blockRangesSearched.add(BlockRange(blockNumber, blockNumber))
            1 -> blockRangeBlockNumberBelongsTo.first().lowerBlock = blockNumber
            else -> Timber.e("Error: Multiple searched block ranges including block number")
        }

        // TODO: This isn't the most efficient way of storing the transactions but for now it will do
        transactionsStorage.storeBlocksSearched(blockRangesSearched)
    }

    // TODO: Try to introduce more functional aspects to this function/algorithm. It's very confusing like this
    // It can at least be optimised by merging neighbouring blocks in blockRangesSearched
    fun getBlocksToSearchFromTopBlock(topBlock: Long, numberOfBlocks: Long): List<BlockRange> {
        if (blockRangesSearched.size == 0) {
            return getInitialBlocksToSearch(topBlock, numberOfBlocks)
        }

        var blocksToSearchCount = numberOfBlocks
        val unsearchedBlockRanges = mutableListOf<BlockRange>()

        blockRangesSearched.sortBy { it.upperBlock }
        val tempBlocksSearched = ArrayList(blockRangesSearched)

        while (blocksToSearchCount > 0 && tempBlocksSearched.size > 0) {

            val newRangeUpperBlock: Long
            val newRangeLowerBlock: Long

            if (unsearchedBlockRanges.size == 0) {
                newRangeUpperBlock = topBlock
                newRangeLowerBlock = tempBlocksSearched.last().upperBlock + EXCLUSIVE_RANGE_OFFSET
            } else if (tempBlocksSearched.size > 1) {
                newRangeUpperBlock = tempBlocksSearched.last().lowerBlock - EXCLUSIVE_RANGE_OFFSET
                newRangeLowerBlock = tempBlocksSearched[tempBlocksSearched.lastIndex - 1].upperBlock + EXCLUSIVE_RANGE_OFFSET
                tempBlocksSearched.removeAt(tempBlocksSearched.lastIndex)
            } else {
                newRangeUpperBlock = tempBlocksSearched.first().lowerBlock - EXCLUSIVE_RANGE_OFFSET
                newRangeLowerBlock = newRangeUpperBlock - blocksToSearchCount
                tempBlocksSearched.removeAt(tempBlocksSearched.lastIndex)
            }

            val newBlockRange = BlockRange(newRangeUpperBlock, newRangeLowerBlock)
            adjustForBlocksToSearchCount(newBlockRange, blocksToSearchCount)
            unsearchedBlockRanges.add(newBlockRange)

            blocksToSearchCount -= newBlockRange.rangeExclusive
        }

        return unsearchedBlockRanges

//        kotlin.run breaker@ {
//            val blocksToSearch = blockRangesSearched.foldIndexed(mutableListOf<BlockRange>(), { index, acc, blockRange ->
//                if (blocksIncludedCount < 0) {
//                    return@breaker
//                }
//
//                val newBlockRange: BlockRange
//
//                if (index == 0) {
//                    newBlockRange = BlockRange(topBlock, blockRange.upperBlock + 1)
//                } else {
//                    newBlockRange = BlockRange(acc[index - 1].lowerBlock, blockRange.upperBlock)
//                }
//
//                blocksIncludedCount -= newBlockRange.rangeExclusive
//                if (blocksIncludedCount > 0) {
//                    acc.add(newBlockRange)
//                } else {
//                    newBlockRange.lowerBlock += -blocksIncludedCount
//                    acc.add(newBlockRange)
//                }
//
//                acc
//            })
//        }

    }

    private fun adjustForBlocksToSearchCount(newBlockRange: BlockRange, blocksAvailableCount: Long) {
        if (blocksAvailableCount < newBlockRange.rangeExclusive) {
            newBlockRange.lowerBlock = newBlockRange.upperBlock - blocksAvailableCount + EXCLUSIVE_RANGE_OFFSET
        }
    }

    private fun getInitialBlocksToSearch(topBlock: Long, numberOfBlocks: Long): List<BlockRange> {
        val lowerBlock = if (numberOfBlocks > topBlock) 0 else topBlock - numberOfBlocks + 1
        return listOf(BlockRange(topBlock, lowerBlock))
    }


}