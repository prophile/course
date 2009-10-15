#include <stdarg.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <math.h>
#include <assert.h>

#define DOUBLE_DIGITS 10

static unsigned write_integer ( FILE* fp, int value, bool isSigned, const char* digits, unsigned pad, char padCharacter );

static unsigned write_double ( FILE* fp, double value, unsigned padWhole, char padWholeCharacter, unsigned decimalDigits )
{
	unsigned length = 0, i, roundedValue;
	// first: special cases
	// handle value == ±infinity or value == nan
	if (isnan(value))
	{
		fwrite("nan", 3, 1, fp);
		return 3;
	}
	if (isinf(value))
	{
		if (value < 0.0)
		{
			fwrite("-inf", 4, 1, fp);
			return 4;
		}
		else
		{
			fwrite("inf", 3, 1, fp);
			return 3;
		}
	}
	// print out the sign if it's a negative
	if (value < 0.0)
	{
		value = -value;
		putc('-', fp);
		length++;
	}
	// write the non-fractional part
	length += write_integer(fp, lrint(floor(value)), false, "0123456789", padWhole, padWholeCharacter);
	// and the decimal place
	putc('.', fp);
	length++;
	// just get the fractional part
	value = value - floor(value);
	for (i = 0; i < decimalDigits; i++)
	{
		// get the decimal digits
		value *= 10.0;
		roundedValue = (int)value;
		assert(roundedValue >= 0);
		assert(roundedValue < 10);
		putc("0123456789"[(int)value], fp); // think about it :)
		length++;
		value = value - floor(value);
	}
	return length;
}

static unsigned write_integer ( FILE* fp, int value, bool isSigned, const char* digits, unsigned pad, char padCharacter )
{
	char stringBuffer[16];
	// we write into this buffer BACKWARDS as a way of reversing the output
	char* activePointer = &(stringBuffer[15]);
	// the radix is just the length of the digit string
	// the compiler will probably inline or partially specialise this, which is nice
	unsigned unsignedValue, digit, radix = strlen(digits), length = 0, paddingLength;
	// handle putting in the -, we can then deal with it as unsigned
	if (isSigned)
	{
		if (value < 0)
		{
			*activePointer-- = '-';
			length++;
			unsignedValue = -value;
		}
		else
		{
			unsignedValue = (unsigned)value;
		}
	}
	else
	{
		unsignedValue = (unsigned)value;
	}
	// if it's just a zero, deal with that
	if (unsignedValue == 0)
	{
		*activePointer-- = digits[0];
		length++;
	}
	else
	{
		// go through and eat digits one at a time with modulo/divide
		// note that this is particularly fast on hex where it turns into uV & 0xF and uV >>= 4
		while (unsignedValue != 0)
		{
			digit = unsignedValue % radix;
			*activePointer-- = digits[digit];
			length++;
			unsignedValue /= radix;
		}
	}
	// if we need padding, write that in now directly to the stream
	paddingLength = length;
	while (paddingLength < pad)
	{
		putc(padCharacter, fp);
		paddingLength++;
	}
	// go back to the last character we wrote
	activePointer++;
	// and print everything in our backwards buffer the right way round
	fwrite(activePointer, length, 1, fp);
	// ... and return how much we wrote
	return length > pad ? length : pad;
}

int simple_fprintf ( FILE* fp, const char* format, ... )
{
	// keep track of these
	int printedCharacters = 0, remainingLength, blockLength;
	const char* stringValue;
	int intValue;
	double doubleValue;
	va_list va;
	va_start(va, format);
	// get the length of the format string
	remainingLength = strlen(format);
	// while we're still not there...
	while (remainingLength > 0)
	{
		// look for the first bit of formatting magic
		char* ptr = strchr(format, '%');
		if (!ptr)
		{
			// wahey, no more %...
			// flush the rest, return
			printedCharacters += remainingLength;
			fwrite(format, remainingLength, 1, fp);
			remainingLength = 0;
			continue;
		}
		else if (ptr != format)
		{
			// we have some characters in between...
			blockLength = ptr - format;
			fwrite(format, blockLength, 1, fp);
			remainingLength -= blockLength;
			printedCharacters += blockLength;
		}
		remainingLength -= 2; // account for the % and the type
		if (!*(ptr + 1))
		{
			// the character after the % is the end of the string...
			// just print a %
			putc('%', fp);
			remainingLength++;
			printedCharacters++;
			continue;
		}
		switch (*(ptr + 1))
		{
			case 's':
				// simple string case
				stringValue = va_arg(va, const char*);
				fwrite(stringValue ? stringValue : "NULL", stringValue? strlen(stringValue) : 4, 1, fp);
				printedCharacters += stringValue ? strlen(stringValue) : 4;
				break;
			// integer cases, all just slight variations on the parameters to write_integer
			case 'd':
				intValue = va_arg(va, int);
				printedCharacters += write_integer(fp, intValue, true, "0123456789", 0, ' ');
				break;
			case 'u':
				intValue = va_arg(va, int);
				printedCharacters += write_integer(fp, intValue, false, "0123456789", 0, ' ');
				break;
			case 'x':
				intValue = va_arg(va, int);
				printedCharacters += write_integer(fp, intValue, false, "0123456789abcdef", 0, ' ');
				break;
			case 'X':
				intValue = va_arg(va, int);
				printedCharacters += write_integer(fp, intValue, false, "0123456789ABCDEF", 0, ' ');
				break;
			// grab the current length
			case 'n':
				*(va_arg(va, int*)) = printedCharacters;
				break;
			// a literal %
			case '%':
				putc('%', fp);
				printedCharacters++;
				break;
			// floating-point number
			// note that varargs will automatically promote floats to doubles
			// varargs kinda sucks to be honest, I'd avoid it if I were you
			case 'f':
				printedCharacters += write_double(fp, va_arg(va, double), 0, ' ', 10);
				break;
			// unknown format character, just write "FAIL" and continue
			default:
				fwrite("FAIL", 4, 1, fp);
				printedCharacters += 4;
				break;
		}
		// go to the next bit of format string
		format = ptr + 2;
	}
	va_end(va);
	return printedCharacters;
}

/* int main ()
{
	simple_fprintf(stdout, "%s: %X %f\n", "cake", 0xFEEDFACE, 12.0);
	return 0;
}*/
