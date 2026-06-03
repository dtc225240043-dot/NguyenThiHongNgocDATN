package com.example.hskmaster.data

sealed class QuestionType {
    data class MultipleChoice(val options: List<String>, val correctIndex: Int) : QuestionType()
    data class TrueFalse(val correctIsTrue: Boolean) : QuestionType()
    data class SentenceOrdering(val words: List<String>, val correctOrder: List<String>) : QuestionType()
}

data class QuizQuestion(
    val id: Int,
    val question: String,
    val type: QuestionType,
    val explanation: String = ""
)

object QuizData {

    fun getQuestionsForLevel(level: String): List<QuizQuestion> {
        return when (level) {
            "HSK 1" -> hsk1Questions
            "HSK 2" -> hsk2Questions
            "HSK 3" -> hsk3Questions
            "HSK 4" -> hsk4Questions
            "HSK 5" -> hsk5Questions
            "HSK 6" -> hsk6Questions
            else -> hsk1Questions
        }
    }

    // ====================== HSK 1 ======================
    private val hsk1Questions = listOf(
        QuizQuestion(1, "'你好' nghĩa là gì?", QuestionType.MultipleChoice(listOf("Tạm biệt", "Xin chào", "Cảm ơn", "Xin lỗi"), 1), "'你好' = xin chào."),
        QuizQuestion(2, "'这是什么？' dịch sang tiếng Việt là?", QuestionType.MultipleChoice(listOf("Đây là gì?", "Ai đó?", "Khi nào?", "Ở đâu?"), 0), "'这是什么？' = Đây là gì?"),
        QuizQuestion(3, "Câu '谢谢' có nghĩa là 'Cảm ơn'", QuestionType.TrueFalse(true), "谢谢 = cảm ơn."),
        QuizQuestion(4, "Sắp xếp các từ thành câu đúng: 我 / 是 / 李明", QuestionType.SentenceOrdering(listOf("是", "李明", "我"), listOf("我", "是", "李明")), "Đúng: 我 是 李明"),
        QuizQuestion(5, "'再见' nghĩa là?", QuestionType.MultipleChoice(listOf("Xin chào", "Tạm biệt", "Cảm ơn", "Xin lỗi"), 1), "再见 = Tạm biệt."),
        QuizQuestion(6, "'他是谁？' hỏi về điều gì?", QuestionType.MultipleChoice(listOf("Đây là gì?", "Anh ấy là ai?", "Khi nào?", "Ở đâu?"), 1), "他是谁 = Anh ấy là ai?"),
        QuizQuestion(7, "Câu '我喜欢喝茶。' có nghĩa là 'Tôi thích uống trà.'", QuestionType.TrueFalse(true), "我喜欢喝茶 = Tôi thích uống trà."),
        QuizQuestion(8, "Sắp xếp các từ thành câu: 这 / 是 / 多少钱", QuestionType.SentenceOrdering(listOf("钱", "多少", "这", "是"), listOf("这", "是", "多少", "钱")), "Đúng: 这 是 多少 钱"),
        QuizQuestion(9, "'几点了？' dịch là?", QuestionType.MultipleChoice(listOf("Bao nhiêu tuổi?", "Mấy giờ rồi?", "Ở đâu?", "Làm gì?"), 1), "几点了 = Mấy giờ rồi?"),
        QuizQuestion(10, "'我不懂。' = 'Tôi không hiểu.'", QuestionType.TrueFalse(true), "我不懂 = Tôi không hiểu."),
        QuizQuestion(11, "'明天见' nghĩa là?", QuestionType.MultipleChoice(listOf("Hẹn gặp lại ngày mai", "Tạm biệt", "Cảm ơn", "Xin chào"), 0), "明天见 = Hẹn gặp lại ngày mai."),
        QuizQuestion(12, "Sắp xếp: 这 / 是 / 我 / 的 / 书", QuestionType.SentenceOrdering(listOf("书", "的", "是", "我", "这"), listOf("这", "是", "我", "的", "书")), "Đúng: 这 是 我 的 书"),
        QuizQuestion(13, "'你多大？' là câu hỏi về tuổi", QuestionType.TrueFalse(true), "你多大 = Bạn bao nhiêu tuổi?"),
        QuizQuestion(14, "'多少钱？' hỏi về điều gì?", QuestionType.MultipleChoice(listOf("Bao nhiêu tiền?", "Mấy giờ?", "Ở đâu?", "Ai?"), 0), "多少钱 = Bao nhiêu tiền?"),
        QuizQuestion(15, "Sắp xếp: 我 / 很 / 好", QuestionType.SentenceOrdering(listOf("好", "很", "我"), listOf("我", "很", "好")), "Đúng: 我 很 好")
    )

    // ====================== HSK 2 ======================
    private val hsk2Questions = listOf(
        QuizQuestion(1, "'今天天气怎么样？' nghĩa là?", QuestionType.MultipleChoice(listOf("Hôm nay thời tiết thế nào?", "Bạn khỏe không?", "Bạn làm gì?", "Ở đâu?"), 0), "今天天气怎么样 = Hôm nay thời tiết thế nào?"),
        QuizQuestion(2, "Sắp xếp thành câu: 我 / 每天 / 早上 / 七点 / 起床", QuestionType.SentenceOrdering(listOf("每天", "起床", "我", "七点", "早上"), listOf("我", "每天", "早上", "七点", "起床")), "Đúng: 我 每天 早上 七点 起床"),
        QuizQuestion(3, "'我可以借一下你的笔吗？' = 'Tôi có thể mượn bút của bạn không?'", QuestionType.TrueFalse(true), "Câu lịch sự xin mượn đồ."),
        QuizQuestion(4, "'他在哪儿工作？' hỏi về?", QuestionType.MultipleChoice(listOf("Anh ấy ở đâu làm việc?", "Anh ấy làm nghề gì?", "Anh ấy ở nhà?", "Anh ấy đi đâu?"), 0), "他在哪儿工作 = Anh ấy làm việc ở đâu?"),
        QuizQuestion(5, "Sắp xếp: 请问 / 洗手间 / 在哪里", QuestionType.SentenceOrdering(listOf("洗手间", "在", "哪里", "请问"), listOf("请问", "洗手间", "在哪里")), "Đúng: 请问 洗手间 在哪里"),
        QuizQuestion(6, "'昨天你去哪儿了？' = 'Hôm qua bạn đã đi đâu?'", QuestionType.TrueFalse(true), "昨天 = hôm qua; 去哪儿 = đi đâu"),
        QuizQuestion(7, "'我吃过了' nghĩa là gì?", QuestionType.MultipleChoice(listOf("Tôi đã ăn rồi", "Tôi sẽ ăn", "Tôi không ăn", "Tôi đang ăn"), 0), "吃过了 = đã ăn rồi"),
        QuizQuestion(8, "Sắp xếp: 你 / 喜欢 / 什么 / 颜色", QuestionType.SentenceOrdering(listOf("什么", "颜色", "你", "喜欢"), listOf("你", "喜欢", "什么", "颜色")), "Đúng: 你 喜欢 什么 颜色"),
        QuizQuestion(9, "'可以' thường dùng để diễn tả 'có thể'", QuestionType.TrueFalse(true), "可以 = có thể/được phép."),
        QuizQuestion(10, "'我在学校' nghĩa là?", QuestionType.MultipleChoice(listOf("Tôi ở trường", "Tôi đi học", "Tôi về nhà", "Tôi đi làm"), 0), "我在学校 = Tôi ở trường."),
        QuizQuestion(11, "Sắp xếp: 今天 / 天气 / 很 / 好", QuestionType.SentenceOrdering(listOf("很", "现在", "今天", "好", "天气"), listOf("今天", "天气", "很", "好")), "Đúng: 今天 天气 很 好"),
        QuizQuestion(12, "'你会说汉语吗？' = 'Bạn có thể nói tiếng Trung không?'", QuestionType.TrueFalse(true), "会 = có thể, nói = 说, 汉语 = tiếng Trung."),
        QuizQuestion(13, "'请慢用' thường nói khi nào?", QuestionType.MultipleChoice(listOf("Khi mời ăn/ăn uống", "Khi tạm biệt", "Khi xin lỗi", "Khi cảm ơn"), 0), "请慢用 = xin mời dùng (mời ăn)."),
        QuizQuestion(14, "Sắp xếp: 我 / 想 / 学 / 中文", QuestionType.SentenceOrdering(listOf("中文", "学", "我", "想"), listOf("我", "想", "学", "中文")), "Đúng: 我 想 学 中文"),
        QuizQuestion(15, "'几点开会？' hỏi về điều gì?", QuestionType.MultipleChoice(listOf("Mấy giờ họp?", "Ở đâu họp?", "Ai tham gia?", "Bao lâu họp?"), 0), "几点 = mấy gi��; 开会 = họp.")
    )

    // ====================== HSK 3 ======================
    private val hsk3Questions = listOf(
        QuizQuestion(1, "'你觉得这个电影怎么样？' nghĩa là?", QuestionType.MultipleChoice(listOf("Bạn thấy bộ phim thế nào?", "Bạn có thích phim này?", "Bộ phim ở đâu?", "Khi nào chiếu?"), 0), "你觉得...怎么样 = bạn nghĩ... thế nào"),
        QuizQuestion(2, "Sắp xếp: 我 / 已经 / 吃过 / 饭 / 了", QuestionType.SentenceOrdering(listOf("饭", "了", "我", "已经", "吃过"), listOf("我", "已经", "吃过", "饭", "了")), "Đúng: 我 已经 吃过 饭 了"),
        QuizQuestion(3, "'地铁站怎么走？' = 'Ga tàu điện ngầm đi như thế nào?'", QuestionType.TrueFalse(true), "怎么走 = đi như thế nào"),
        QuizQuestion(4, "'我正在学习汉语' diễn tả gì?", QuestionType.MultipleChoice(listOf("Tôi đang học tiếng Trung", "Tôi đã học xong", "Tôi sẽ học", "Tôi không học"), 0), "正在 = đang (hành động đang diễn ra)."),
        QuizQuestion(5, "Sắp xếp: 如果 / 明天 / 下雨 / 我 / 不 / 去", QuestionType.SentenceOrdering(listOf("不", "下雨", "如果", "我", "去", "明天"), listOf("如果", "明天", "下雨", "我", "不", "去")), "Đúng: 如果 明天 下雨 我 不 去"),
        QuizQuestion(6, "'他已经回家了' = 'Anh ấy đã về nhà rồi'", QuestionType.TrueFalse(true), "已经 = đã"),
        QuizQuestion(7, "'为什么你迟到？' hỏi về gì?", QuestionType.MultipleChoice(listOf("Tại sao bạn đến muộn?", "Bạn sống ở đâu?", "Bạn đang làm gì?", "Bạn có bao nhiêu tuổi?"), 0), "为什么 = tại sao"),
        QuizQuestion(8, "Sắp xếp: 我们 / 去 / 酒店 / 吃饭", QuestionType.SentenceOrdering(listOf("吃饭", "我们", "酒店", "去"), listOf("我们", "去", "酒店", "吃饭")), "Đúng: 我们 去 酒店 吃饭"),
        QuizQuestion(9, "'已经' thường dùng để chỉ điều gì?", QuestionType.MultipleChoice(listOf("Hành động đã hoàn thành", "Hành động đang diễn ra", "Sự miêu tả tương lai", "Hỏi câu"), 0), "已经 = đã (hoàn thành)."),
        QuizQuestion(10, "'你应该休息' = 'Bạn nên nghỉ ngơi'", QuestionType.TrueFalse(true), "应该 = nên"),
        QuizQuestion(11, "Sắp xếp: 他 / 给 / 我 / 了 / 一本 / 书", QuestionType.SentenceOrdering(listOf("给", "他", "一本", "我", "书", "了"), listOf("他", "给", "我", "了", "一本", "书")), "Lưu ý: dùng cấu trúc '给' để chỉ tặng"),
        QuizQuestion(12, "'只要...就...' cấu trúc có nghĩa gì?", QuestionType.MultipleChoice(listOf("Chỉ cần... thì...", "Mặc dù... nhưng...", "Nếu... thì...", "Ngay cả khi..."), 0), "只要...就... = chỉ cần... thì..."),
        QuizQuestion(13, "'我不但会唱歌，而且会跳舞' câu này đúng", QuestionType.TrueFalse(true), "不但...而且... = không chỉ... mà còn..."),
        QuizQuestion(14, "Sắp xếp: 昨天 / 我 / 看 / 电影 / 了", QuestionType.SentenceOrdering(listOf("看", "电影", "我", "了", "昨天"), listOf("昨天", "我", "看", "电影", "了")), "Đúng: 昨天 我 看 电影 了"),
        QuizQuestion(15, "'跟...一样' có nghĩa là?", QuestionType.MultipleChoice(listOf("Giống với...", "Khác với...", "Hơn...", "Ít hơn..."), 0), "跟...一样 = giống với...")
    )

    // ====================== HSK 4 ======================
    private val hsk4Questions = listOf(
        QuizQuestion(1, "'尽管' có nghĩa là?", QuestionType.MultipleChoice(listOf("Mặc dù", "Vì vậy", "Nếu", "Mặc kệ"), 0), "尽管 = mặc dù"),
        QuizQuestion(2, "Sắp xếp: 虽然 / 很累 / 他 / 还是 / 去上班", QuestionType.SentenceOrdering(listOf("还是", "很累", "上班", "虽然", "他"), listOf("虽然", "他", "很累", "还是", "去上班")), "Đúng: 虽然 他 很累 还是 去上班"),
        QuizQuestion(3, "'无论...都...' cấu trúc nghĩa là?", QuestionType.MultipleChoice(listOf("Dù... thì...", "Nếu... thì...", "Chỉ khi... thì...", "Kể cả... cũng..."), 0), "无论...都... = dù... thì..."),
        QuizQuestion(4, "'他已经习惯了北京的生活' câu này đúng", QuestionType.TrueFalse(true), "已经习惯 = đã quen"),
        QuizQuestion(5, "Sắp xếp: 如果 / 你 / 想 / 可以 / 我们 / 一起 / 去", QuestionType.SentenceOrdering(listOf("一起", "可以", "如果", "去", "我们", "你", "想"), listOf("如果", "你", "想", "我们", "可以", "一起", "去")), "Đúng: 如果 你 想 我们 可以 一起 去"),
        QuizQuestion(6, "'与其...不如...' nghĩa là?", QuestionType.MultipleChoice(listOf("Thà... còn hơn...", "Mặc dù... nhưng...", "Không... cũng...", "Nếu... thì..."), 0), "与其...不如... = thà... còn hơn..."),
        QuizQuestion(7, "'他对工作很认真' = 'Anh ấy rất nghiêm túc trong công việc.'", QuestionType.TrueFalse(true), "对...认真 = nghiêm túc về..."),
        QuizQuestion(8, "Sắp xếp: 这个 / 问题 / 我们 / 需要 / 讨论", QuestionType.SentenceOrdering(listOf("问题", "我们", "讨论", "需要", "这个"), listOf("这个", "问题", "我们", "需要", "讨论")), "Đúng: 这个 问题 我们 需要 讨论"),
        QuizQuestion(9, "'一旦...就...' chỉ điều gì?", QuestionType.MultipleChoice(listOf("Một khi... thì...", "Mặc dù... thì...", "Trong khi...", "Nếu không..."), 0), "一旦...就... = một khi... thì..."),
        QuizQuestion(10, "'他不仅会弹钢琴，而且会拉小提琴' đúng", QuestionType.TrueFalse(true), "不仅...而且... = không chỉ... mà còn..."),
        QuizQuestion(11, "Sắp xếp: 我 / 已经 / 决定 / 离开 / 了", QuestionType.SentenceOrdering(listOf("已经", "了", "决定", "我", "离开"), listOf("我", "已经", "决定", "离开", "了")), "Đúng: 我 已经 决定 离开 了"),
        QuizQuestion(12, "'只是' thường dùng để diễn tả?", QuestionType.MultipleChoice(listOf("Chỉ là", "Ngay cả", "Vẫn còn", "Hoàn toàn"), 0), "只是 = chỉ là"),
        QuizQuestion(13, "'虽然他年纪小, 可是很聪明' câu này đúng", QuestionType.TrueFalse(true), "虽然...可是... = mặc dù... nhưng..."),
        QuizQuestion(14, "Sắp xếp: 如果 / 我 / 有钱 / 我 / 会 / 环游 / 世界", QuestionType.SentenceOrdering(listOf("世界", "会", "有钱", "我", "环游", "如果", "我"), listOf("如果", "我", "有钱", "我", "会", "环游", "世界")), "Đúng: 如果 我 有钱 我 会 环游 世界"),
        QuizQuestion(15, "'逐渐' có nghĩa là?", QuestionType.MultipleChoice(listOf("Dần dần", "Đột ngột", "Hoàn toàn", "Ngay lập tức"), 0), "逐渐 = dần dần")
    )

    private val hsk5Questions = listOf(
        QuizQuestion(1, "'尽快' nghĩa là?", QuestionType.MultipleChoice(listOf("Càng sớm càng tốt", "Chậm rãi", "Không bao giờ", "Có thể"), 0), "尽快 = càng sớm càng tốt"),
        QuizQuestion(2, "Sắp xếp: 要是 / 我 / 知道 / 我 / 会 / 告诉 / 你", QuestionType.SentenceOrdering(listOf("告诉", "会", "要是", "我", "知道", "你", "我"), listOf("要是", "我", "知道", "我", "会", "告诉", "你")), "Đúng: 要是 我 知道 我 会 告诉 你"),
        QuizQuestion(3, "'他无意中看到了那封信' câu này đúng", QuestionType.TrueFalse(true), "无意中 = tình cờ/không cố ý"),
        QuizQuestion(4, "'鉴于' thường dùng trong văn viết để biểu thị?", QuestionType.MultipleChoice(listOf("Xét về..., do...", "Vì vậy", "Mặc dù", "Trừ khi"), 0), "鉴于 = xét về/ vì"),
        QuizQuestion(5, "Sắp xếp: 我们 / 必须 / 找到 / 一个 / 解决 / 方法", QuestionType.SentenceOrdering(listOf("必须", "方法", "找到", "我们", "一个", "解决"), listOf("我们", "必须", "找到", "一个", "解决", "方法")), "Đúng: 我们 必须 找到 一个 解决 方法"),
        QuizQuestion(6, "'无论如何' có nghĩa là?", QuestionType.MultipleChoice(listOf("Dù bằng cách nào", "Nếu không thì", "Vì lý do đó", "Không phải"), 0), "无论如何 = dù bằng cách nào / anyway"),
        QuizQuestion(7, "'这项计划失败了' = 'Kế hoạch này thất bại'", QuestionType.TrueFalse(true), "失败 = thất bại"),
        QuizQuestion(8, "Sắp xếp: 他 / 提出 / 了 / 很多 / 建议", QuestionType.SentenceOrdering(listOf("很多", "建议", "了", "提出", "他"), listOf("他", "提出", "了", "很多", "建议")), "Đúng: 他 提出了 很多 建议"),
        QuizQuestion(9, "'采用' có nghĩa là?", QuestionType.MultipleChoice(listOf("Áp dụng/chấp nhận", "Từ chối", "Tránh", "Thảo luận"), 0), "采用 = áp dụng, chấp nhận"),
        QuizQuestion(10, "'倘若' tương đương với từ nào?", QuestionType.TrueFalse(true), "倘若 = nếu"),
        QuizQuestion(11, "Sắp xếp: 由于 / 天气 / 我们 / 取消 / 了 / 旅行", QuestionType.SentenceOrdering(listOf("旅行", "取消", "由于", "了", "天气", "我们"), listOf("由于", "天气", "我们", "取消", "了", "旅行")), "Đúng: 由于 天气 我们 取消 了 旅行"),
        QuizQuestion(12, "'随著' / '随着' thường có nghĩa là?", QuestionType.MultipleChoice(listOf("Cùng với/ theo với", "Trái ngược với", "Loại trừ", "Tăng lên"), 0), "随着 = cùng với/ theo với"),
        QuizQuestion(13, "'如果没有...' dùng để biểu thị điều gì?", QuestionType.TrueFalse(true), "Nếu không có... thì..."),
        QuizQuestion(14, "Sắp xếp: 他 / 最终 / 决定 / 离开 / 公司", QuestionType.SentenceOrdering(listOf("最终", "决定", "他", "公司", "离开"), listOf("他", "最终", "决定", "离开", "公司")), "Đúng: 他 最终 决定 离开 公司"),
        QuizQuestion(15, "'显然' nghĩa là?", QuestionType.MultipleChoice(listOf("Rõ ràng", "Có thể", "Ngẫu nhiên", "Khó hiểu"), 0), "显然 = rõ ràng")
    )

    private val hsk6Questions = listOf(
        QuizQuestion(1, "'毋宁' có nghĩa tương tự với?", QuestionType.MultipleChoice(listOf("Thà... hơn là...", "Không... mà...", "Bởi vì", "Nếu"), 0), "毋宁 = thà... hơn là..."),
        QuizQuestion(2, "Sắp xếp: 即便 / 他 / 知道 / 也 / 不 / 会 / 改变", QuestionType.SentenceOrdering(listOf("他", "会", "改变", "即便", "知道", "也", "不"), listOf("即便", "他", "知道", "也", "不", "会", "改变")), "Đúng: 即便 他 知道 也 不 会 改变"),
        QuizQuestion(3, "'固然' thường dùng để biểu thị?", QuestionType.MultipleChoice(listOf("Chắc chắn/ quả thật", "Có lẽ", "Không bao giờ", "Nếu"), 0), "固然 = quả thật/ chắc chắn"),
        QuizQuestion(4, "'他言语中的含义并不简单' câu này đúng", QuestionType.TrueFalse(true), "含义 = hàm ý/ý nghĩa"),
        QuizQuestion(5, "Sắp xếp: 这种 / 问题 / 需要 / 更加 / 深入 / 的 / 研究", QuestionType.SentenceOrdering(listOf("更加", "研究", "问题", "深入", "需要", "这种", "的"), listOf("这种", "问题", "需要", "更加", "深入", "的", "研究")), "Đúng: 这种 问题 需要 更加 深入 的 研究"),
        QuizQuestion(6, "'未必' nghĩa là?", QuestionType.MultipleChoice(listOf("Chưa chắc", "Tất nhiên", "Luôn luôn", "Phải"), 0), "未必 = chưa chắc"),
        QuizQuestion(7, "'倘若...便...' cấu trúc hợp lệ", QuestionType.TrueFalse(true), "倘若...便... = nếu... thì..."),
        QuizQuestion(8, "Sắp xếp: 文章 / 的 / 主旨 / 在于 / 讨论", QuestionType.SentenceOrdering(listOf("于", "主旨", "讨论", "文章", "的", "在"), listOf("文章", "的", "主旨", "在于", "讨论")), "Đúng: 文章 的 主旨 在于 讨论"),
        QuizQuestion(9, "'归根结底' nghĩa là?", QuestionType.MultipleChoice(listOf("Rốt cuộc/ cuối cùng", "Ngay lập tức", "Không liên quan", "Phần lớn"), 0), "归根结底 = rốt cuộc, cuối cùng"),
        QuizQuestion(10, "'凡是' có nghĩa là 'bất cứ...'", QuestionType.TrueFalse(true), "凡是 = bất cứ/ mọi"),
        QuizQuestion(11, "Sắp xếp: 在 / 很大 / 程度 / 上 / 这种 / 变化 / 影响 / 了 / 社会", QuestionType.SentenceOrdering(listOf("程度", "变化", "社会", "在", "很大", "上", "影响", "了", "这种"), listOf("在", "很大", "程度", "上", "这种", "变化", "影响", "了", "社会")), "Đúng: 在 很大 程度 上 这种 变化 影响 了 社会"),
        QuizQuestion(12, "'姑且' thường dùng khi nào?", QuestionType.MultipleChoice(listOf("Tạm thời", "Chắc chắn", "Hoàn toàn", "Luôn luôn"), 0), "姑且 = tạm thời"),
        QuizQuestion(13, "'毋宁' và '宁可' mang ý tương tự", QuestionType.TrueFalse(true), "cả hai đều thể hiện 'thà... hơn...' trong ngữ cảnh phù hợp"),
        QuizQuestion(14, "Sắp xếp: 如果 / 再 / 没有 / 措施 / 情况 / 会 / 更加 / 严重", QuestionType.SentenceOrdering(listOf("没有", "更加", "情况", "再", "如果", "会", "严重", "措施"), listOf("如果", "再", "没有", "措施", "情况", "会", "更加", "严重")), "Đúng: 如果 再 没有 措施 情况 会 更加 严重"),
        QuizQuestion(15, "'莫非' dùng để biểu thị gì?", QuestionType.MultipleChoice(listOf("Không lẽ là...", "Chắc chắn là...", "Có lẽ không", "Hoàn toàn"), 0), "莫非 = không lẽ... (thường dùng để hỏi mang tính nghi vấn)")
    )
}