package btvn;

import java.util.Arrays;

public class fibonacci {
	// Hàm để cộng hai mảng đại diện cho hai số rất lớn
	public static int[] addLargeNumbers(int[] num1, int[] num2) {
		int maxLength = Math.max(num1.length, num2.length);
		int[] result = new int[maxLength + 1]; // Kết quả có thể nhiều hơn 1 chữ số
		int carry = 0;

		for (int i = 0; i < maxLength; i++) {
			int digit1 = (i < num1.length) ? num1[i] : 0;
			int digit2 = (i < num2.length) ? num2[i] : 0;

			int sum = digit1 + digit2 + carry;
			result[i] = sum % 10; // Lưu chữ số tại vị trí i
			carry = sum / 10; // Lưu số nhớ (nếu có)
		}

		if (carry > 0) {
			result[maxLength] = carry; // Lưu số nhớ cuối cùng
		}

		return result;
	}

	// Hàm tính số Fibonacci thứ n bằng cách lưu từng chữ số vào mảng
	public static void largeFibonacci(int n) {
		// Khởi tạo số Fibonacci thứ 1 và thứ 2 (1 và 1)
		int[] fib1 = { 1 };
		int[] fib2 = { 1 };

		int[] currentFib = new int[0];

		for (int i = 1; i <= n; i++) {
			if (i == 1 || i == 2) {
				System.out.println(1);
			} else {
				currentFib = addLargeNumbers(fib1, fib2); // Cộng hai số Fibonacci trước đó

				// Cập nhật fib1 và fib2
				fib1 = fib2;
				fib2 = currentFib;
				printLargeNumber(currentFib);
			}
		}

	}

	// Hàm in số lớn từ mảng chữ số
	public static void printLargeNumber(int[] number) {
		// Loại bỏ các số 0 ở cuối (do số lưu ngược)
		int startIndex = number.length - 1;
		while (startIndex > 0 && number[startIndex] == 0) {
			startIndex--;
		}

		// In ngược lại các chữ số để hiển thị đúng thứ tự
		for (int i = startIndex; i >= 0; i--) {
			System.out.print(number[i]);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		largeFibonacci(100);
	}
}
