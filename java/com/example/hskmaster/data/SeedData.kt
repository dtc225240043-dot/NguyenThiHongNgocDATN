
package com.example.hskmaster.data

import com.example.hskmaster.data.local.AppDatabase
import com.example.hskmaster.data.local.entity.*

object SeedData {

    suspend fun seed(db: AppDatabase) {

        val topicDao = db.topicDao()
        val vocabDao = db.vocabDao()
        val grammarDao = db.grammarDao()

        // CHỈ SEED 1 LẦN
        if (topicDao.getTopicsByLevel(1).isNotEmpty()) {
            return
        }



        topicDao.insertAll(

            listOf(

                // HSK1
                TopicEntity(
                    id = "1",
                    title = "HSK1 - Bài 1: Chào hỏi",
                    videoUrl = "https://res.cloudinary.com/dibj4cvyl/video/upload/q_auto/f_auto/v1778256474/7805762234811_zryhsg.mp4",
                    level = 1
                ),

                TopicEntity(
                    id = "2",
                    title = "HSK1 - Bài 2: Gia đình",
                    videoUrl = "https://res.cloudinary.com/demo/video/upload/sample.mp4",
                    level = 1
                ),

                // HSK2
                TopicEntity(
                    id = "3",
                    title = "HSK2 - Bài 1: Mua sắm",
                    videoUrl = "https://res.cloudinary.com/demo/video/upload/sample.mp4",
                    level = 2
                ),

                TopicEntity(
                    id = "4",
                    title = "HSK2 - Bài 2: Thời tiết",
                    videoUrl = "https://res.cloudinary.com/demo/video/upload/sample.mp4",
                    level = 2
                )
            )
        )



        vocabDao.insertAll(

            listOf(

                // HSK1 - Chào hỏi
                VocabEntity(
                    id = "1",
                    hanzi = "你好",
                    topicId = "1",
                    word = "hello",
                    pinyin = "nǐ hǎo",
                    meaning = "xin chào",
                    level = 1,
                    category = "HSK1"
                ),

                VocabEntity(
                    id = "2",
                    hanzi = "谢谢",
                    topicId = "1",
                    word = "thanks",
                    pinyin = "xièxie",
                    meaning = "cảm ơn",
                    level = 1,
                    category = "HSK1"
                ),

                VocabEntity(
                    id = "3",
                    hanzi = "爸爸",
                    topicId = "2",
                    word = "father",
                    pinyin = "bàba",
                    meaning = "bố",
                    level = 1,
                    category = "HSK1"
                ),

                VocabEntity(
                    id = "4",
                    hanzi = "妈妈",
                    topicId = "2",
                    word = "mother",
                    pinyin = "māma",
                    meaning = "mẹ",
                    level = 1,
                    category = "HSK1"
                ),

                VocabEntity(
                    id = "5",
                    hanzi = "买",
                    topicId = "3",
                    word = "buy",
                    pinyin = "mǎi",
                    meaning = "mua",
                    level = 2,
                    category = "HSK2"
                ),

                VocabEntity(
                    id = "6",
                    hanzi = "天气",
                    topicId = "4",
                    word = "weather",
                    pinyin = "tiānqì",
                    meaning = "thời tiết",
                    level = 2,
                    category = "HSK2"
                ),

                VocabEntity(
                    id = "7",
                    hanzi = "旅行",
                    topicId = "travel",
                    word = "travel",
                    pinyin = "lǚxíng",
                    meaning = "du lịch",
                    level = 1,
                    category = "TRAVEL"
                ),

                VocabEntity(
                    id = "8",
                    hanzi = "电脑",
                    topicId = "technology",
                    word = "computer",
                    pinyin = "diànnǎo",
                    meaning = "máy tính",
                    level = 1,
                    category = "TECHNOLOGY"
                )
            )
        )



        grammarDao.insertAll(

            listOf(

                GrammarEntity(
                    id = "1",
                    topicId = "1",
                    content = "你好 là câu chào cơ bản"
                ),

                GrammarEntity(
                    id = "2",
                    topicId = "2",
                    content = "这是我爸爸 = Đây là bố tôi"
                ),

                GrammarEntity(
                    id = "3",
                    topicId = "3",
                    content = "我想买东西 = Tôi muốn mua đồ"
                ),

                GrammarEntity(
                    id = "4",
                    topicId = "4",
                    content = "今天天气很好 = Hôm nay thời tiết đẹp"
                )
            )
        )
    }
}

