import idautils
import idaapi
import idc

# 打开一个文件用于写入函数名称
with open("C://Users//LLP//Desktop//因式分解//minecraftpe..txt", "w") as file:
	# 遍历所有函数
	for function_ea in idautils.Functions():

		function_name = idc.get_func_name(function_ea)
		file.write(function_name + "\n")

print("Function names have been exported.")