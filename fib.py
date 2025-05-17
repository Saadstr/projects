def fibonacci(n):
    if n <= 0:
        return []
    elif n == 1:
        return [0]
    elif n == 2:
        return [0, 1]
    result = [0, 1]
    for _ in range(2, n):
        next_num = result[-1] + result[-2]
        result.append(next_num)
    return result
n = int(input("Enter the number of Fibonacci numbers to generate: "))
result = fibonacci(n)
print(result)
