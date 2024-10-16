package TH1;

public interface ProgrammingLanguage {
	// Trả về tên của ngôn ngữ lập trình
	String getName();

	// Trả về tác giả/người phát triển ngôn ngữ
	String getAuthor();

	// Trả về năm phát hành của ngôn ngữ
	int getReleaseYear();

	String getUsage();

	// In thông tin của ngôn ngữ lập trình
	void input();

	void output();
}
