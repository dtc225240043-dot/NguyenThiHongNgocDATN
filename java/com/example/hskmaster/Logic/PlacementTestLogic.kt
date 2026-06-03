package com.example.hskmaster.logic

class PlacementTestLogic {

    // Hàm tính điểm dựa trên các câu trả lời
    fun calculateScore(testModel: PlacementTestModel): Int {
        var score = 0

        if (testModel.question1.startsWith("B")) score++
        if (testModel.question2.startsWith("A")) score++
        if (testModel.question3.startsWith("A")) score++
        if (testModel.question4.startsWith("A")) score++
        if (testModel.question5.startsWith("B")) score++
        if (testModel.question6.startsWith("C")) score++
        if (testModel.question7.startsWith("B")) score++
        if (testModel.question8.startsWith("B")) score++
        if (testModel.question9.startsWith("B")) score++
        if (testModel.question10.startsWith("C")) score++

        return score
    }

    // Hàm phân loại trình độ và kết luận dựa trên số câu đúng
    fun classifyLevel(score: Int): String {
        return when (score) {
            in 0..1 -> "Trình độ: Chưa biết gì\nĐiểm mạnh: Chưa có nền tảng\nĐiểm yếu: Từ vựng, phát âm, ngữ pháp"
            in 2..3 -> "Trình độ: Biết nói một chút\nĐiểm mạnh: Nhận biết chào hỏi cơ bản\nĐiểm yếu: Câu hoàn chỉnh, ngữ pháp"
            in 4..5 -> "Trình độ: HSK1\nĐiểm mạnh: Từ vựng cơ bản\nĐiểm yếu: Ngữ pháp & phản xạ câu"
            6 -> "Trình độ: HSK2 (đang lên HSK3)\nĐiểm mạnh: Câu đơn + từ vựng nền\nĐiểm yếu: Ngữ pháp trung cấp"
            7 -> "Trình độ: HSK3\nĐiểm mạnh: Hiểu câu & giao tiếp cơ bản\nĐiểm yếu: Câu phức & cấu trúc nâng cao"
            in 8..9 -> "Trình độ: HSK4\nĐiểm mạnh: Ngữ pháp & đọc hiểu tốt\nĐiểm yếu: Độ tự nhiên & câu nâng cao"
            10 -> "Trình độ: HSK5\nĐiểm mạnh: Sử dụng tiếng Trung linh hoạt\nĐiểm yếu: Tinh chỉnh & học thuật"
            else -> "Trình độ không xác định"
        }
    }
}