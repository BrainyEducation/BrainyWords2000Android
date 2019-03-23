# Words2k-Android

## Enviroment

* Android studio 3.0+

## BRANCH RULES
	<Task_number>_<Account>_<Task_name>

## COMMIT RULES

* Implement new task:
	"imp. description #Task_number"

* Add new file in task:
	"add. description #Task_number"

* Update file in task:
	"mod. description #Task_number"

* Delete file in task:
	"del. description #Task_number"
	
* Fix bug
    "fix. description #Task_number"

## BEFORE COMMIT

* Đã dùng chức năng "Reformat Code", "Optimize Import", "Rearrange Code" trước khi commit.
* Sửa hết warning lỗi chính tả. (trừ những trường hợp đặc biệt như Từ của Dự án...)
* Dùng Timber.e(e, "Nội dung log") để print exception, ko dc e.printStackTrace or Log(e.getMessage).
* Đặt tên cho view cần đặt theo format: mXyz<Tên của view đó>: Ví du: mAvatarTextView.
* Enum phải theo style sau đây - enum Fruit {APPLE, ORANGE, BANANA, PEAR};
* Review scope của biến, của method và của class. Chọn scope "vừa đủ" theo thứ tự: private, protected, package, public.
* Xoá những blank line không cần thiết. Có những chỗ cần blank line để code dễ đọc, nhưng có những chỗ cần phải xoá. Ví dụ: cuối class, cuối method.
* Những dòng code được để trong // cần phải xoá đi. Nếu thực sự có vấn đề cần xem xét sau thì phải comment TODO và ghi rõ lí do.