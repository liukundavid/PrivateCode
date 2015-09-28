# The program takes 3 input arguments from the command line. 
# The first should be the input filename, the second should 
# be the output type (HTML or text),the third should be an 
# argument that determines the css style to apply (if html).
# This third argument should be disregarded if outputting text.
# 
# Examples
# 
#   ruby 663938-project-one.rb “input1.txt” “html” 1 
#   
# Returns nothing, but stdout a HTML or text in terminal
# 
# Author:       Kun Liu
# Student ID:   663938
# Student Email:kliu2@student.unimelb.edu.au 

# This class is to decrypt a file containing text that has been
# encrypted as a Caesar Cipher.
# 
# The get_shift_number method is to work out the optimal encryption 
# key(the number of letters the text has been shifted by). 
# 
# The decrypt method can decrypt the source file. 
# 
# The output_file is to output the decrypted text as a text file 
# or html file
class CaesarCipher

  # Public: Initialize a CaesarCipher, 
  # 
  # inputname  - the first parameter taken from the command line
  # format     - the second parameter taken from the command line
  # cssfile    - the third parameter taken from the command line
  # 
  # Examples
  # 
  #   CaesarCipher.new("input1.txt","html",1)
  # 
  # Returns nothing
  def initialize(inputname,format,cssfile)
    # declare two class variables to store the encrypted and decrypted dictionaries
    # 
    # Examples
    #   a => c, b => d, c =>e, ... A => C, B => D, C => E, ...
    @encrypter_hash = Hash[]
    @decrypter_hash = Hash[]

    # declare two class variables to store outputformat and css filename
    @outputformat = format
    @cssfilename = cssfile

    # invoke this method to extract the string from the input file
    get_input_text(inputname)
  end

  # all methods that follow will be made private:
  # not accessible for outside objects
  private

  # Private: Define the encrypt and decrypt dictionaries for a given shift number.
  # Because the input file may have both uppercase letters and lowercase letters,
  # so both uppercase and lowercase dictionary should be needed. The style of the
  # dictionary should be {raw character => decrypted character}
  # 
  # shift - the key number of Caesar Cipher
  # 
  # Examples
  # 
  #   define_alphabet(2)
  #   # => @encrypter_hash = {a => c, b => d, c =>e, ... A => C, B => D, C => E, ...}
  #   # => @decrypter_hash = {a => y, b => z, c =>a, ... A => Y, B => Z, C => A, ...}
  #   
  # Returns nothing
  def define_alphabet(shift)
    # convert a range of the alphabetic characters to a string
    # lowercase_alphabet: "abcdefghijklmnopqrstuvwxyz"
    lowercase_alphabet = ('a'..'z').to_a.join
    # uppercase_alphabet: "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    uppercase_alphabet = ('A'..'Z').to_a.join

    # convert a string of the alphabetic characters to an array
    # lowercase_chars :
    # ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
    #  "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"]
    lowercase_chars = lowercase_alphabet.chars.to_a
    # uppercase_chars :
    # ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", 
    #  "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"] 
    uppercase_chars = uppercase_alphabet.chars.to_a

    # create a map value for each lowercase alphabetic character by shift number
    lowercase_encrypter_hash = Hash[lowercase_chars.zip(lowercase_chars.rotate(shift))]
    lowercase_decrypter_hash = Hash[lowercase_chars.zip(lowercase_chars.rotate(-shift))]

    # create a map value for each uppercase alphabetic character by shift number
    uppercase_encrypter_hash = Hash[uppercase_chars.zip(uppercase_chars.rotate(shift))]
    uppercase_decrypter_hash = Hash[uppercase_chars.zip(uppercase_chars.rotate(-shift))]

    # merge the uppercase hash value into the lowercase hash value
    @encrypter_hash = lowercase_encrypter_hash.merge(uppercase_encrypter_hash)
    @decrypter_hash = lowercase_decrypter_hash.merge(uppercase_decrypter_hash)
  end

  # Private: encrypt the "string" to a encrypted string
  # this method use regular expression to replace the raw
  # character with the according encrypted character in 
  # the dictionary(@encrypter_hash)
  # 
  # string - the string to be encrypted
  # 
  # Returns the encrypted string
  def encrypt(string)
    return string.gsub(/[a-zA-Z]/,@encrypter_hash)
  end

  # Private: decrypt the "string" to a decrypted string
  # this method use regular expression to replace the raw
  # character with the according decrypted character in 
  # the dictionary(@decrypter_hash)
  # 
  # string - the string to be decrypted
  # 
  # Returns the decrypted string
  def decrypt(string)
    return string.gsub(/[a-zA-Z]/,@decrypter_hash)
  end

  # Private: extract the string from the input file
  # 
  # inputname     - the filename to be open 
  # 
  # Returns nothing
  def get_input_text(inputname)
    # declare a variable to store the string of the file
    input_text = ""
     
    # verify the existence of "inputname"
    # File.open(inputname,"r") if true 
    # output a re-input message if false
    if File.exist?(inputname)
        # open the "inputname" file and extract the string 
        # of each line into the variable "input_text"
        File.readlines(inputname,"r").each{|line| input_text << line}
        # invoke match_words method to extract each word from the string,
        # get the optimal shift number and output the expected file
        match_words(input_text)
    else
      puts "#{inputname} is not exist,please try to input an existed file name"
    end
  end

  # Private: extract each word from the string, 
  # get the optimal shift number and output the expected file
  # 
  # text          - the string of the file
  # 
  # Returns nothing
  def match_words(text)
    # declare a variable to store all the words in the "text"
    words = []
    # use the regular expression to extract each word by space
    # and convert all the capital letters into lowercase
    # then strip the space around the words
    text.scan(/ ?\w+ ?/).each{ |word| words << word.downcase.strip } 

    # invoke get_shift_number method to get the optimal shift number
    shift = get_shift_number(words)
    # define the alphabet dictionary for each character
    define_alphabet(shift)
    # invoke output_file method to output the expected file
    output_file(decrypt(text),shift)
  end

  # Private: output the decrypted strings as a txt or html file
  # for the html file, it would reference an given css file
  # when it is open.
  # 
  # text  - the expected string to be outputed
  # shift - should be displayed in the output file
  # 
  # Examples
  # For the text file:
  #   
  #   The decrypted text is:
  #   
  #   content here
  #   
  #   Which was shifted by 2 letters.  
  # 
  # Returns a file
  def output_file(text,shift)
    #File.open("663938.#{@outputformat}",'w') do |newfile|
      if @outputformat.eql?("html")
        # output a html file
        # replace all the newlines in the file with "<br>"
        html_text = text.gsub(/\n/,"<br>")
        # format the html code
        puts """
        <!DOCTYPE html>
        <html>
        <head>
        <link rel=\"stylesheet\" type=\"text/css\" href=\"#{@cssfilename}.css\"/>
        </head>
        <body>
        <div id=\"headContent\">The decrypted text is:</div><br>
        <div id=\"mainContent\">#{ html_text }</div><br>
        <div id=\"tailContent\">Which was shifted by #{ shift } letters.</div><br>
        </body>
        </html>
        """
      else
        # output a text file
        puts "The decrypted text is:\n\n#{ text }\n\n"
        puts "Which was shifted by #{ shift } letters."
      end
      #newfile.close  
    #end
    
  end
  
  # Private: this method is to caculate the optimal key number of 
  # Caesar Cipher, the algorithm is to caculate the sum of square of the 
  # difference between the frequency of character in the file and the defined 
  # character frequency, and the smallest among the sums could be the optimal value.
  #
  # word - an array of words to be analysised to get the optimal shift number
  # 
  # Returns optimal_shift
  def get_shift_number(words)
    # declare the return value
    optimal_shift = 0

    # define the alphabetic characters frequency of plaintext language in order:
    # e => 12.02% t => 9.10% a => 8.12% o => 7.68% i => 7.31% n => 6.95% s => 6.28% 
    # r => 6.02% h => 5.92% d => 4.32% l => 3.98% u => 2.88% c => 2.71% m => 2.61% 
    # f => 2.30% y => 2.11% w => 2.09% g => 2.03% p => 1.82% b => 1.49% v => 1.11% 
    # k => 0.69% x => 0.17% j => 0.10% q => 0.10% z => 0.07%   
    defined_character_frequency =
     # {"e" => 12.02, "t" => 9.10, "a" => 8.12, "o" => 7.68, "i" => 7.31, "n" => 6.95,
     #  "s" => 6.28, "r" => 6.02, "h" => 5.92, "d" => 4.32, "l" => 3.98, "u" => 2.88, 
     #  "c" => 2.71, "m" => 2.61, "f" => 2.30, "y" => 2.11, "w" => 2.09, "g" => 2.03, 
     #  "p" => 1.82, "b" => 1.49, "v" => 1.11, "k" => 0.69, "x" => 0.17, "j" => 0.10, 
     #  "q" => 0.10, "z" => 0.07}
     {"e" => 13.0, "t" => 9.0, "a" => 7.8, "o" => 8.2, "i" => 6.8, "n" => 7.3,
      "s" => 6.5, "r" => 6.6, "h" => 5.8, "d" => 4.1, "l" => 3.6, "u" => 2.8, 
      "c" => 2.9, "m" => 2.6, "f" => 2.9, "y" => 1.5, "w" => 1.5, "g" => 1.4, 
      "p" => 2.1, "b" => 1.3, "v" => 1.0, "k" => 0.4, "x" => 0.3, "j" => 0.2, 
      "q" => 0.1, "z" => 0.1}
    
    # store all the possible shift number and the relative sum of square of 
    # the differences
    # 
    # Examples
    # 
    #   shift => sum
    #   1=>543.6586895424836, 
    #   2=>419.64579150326784, 
    #   3=>647.8746032679737, 
    #   4=>551.4365522875818,
    #   ...
    #   9=>172.07564248366018
    shift_sum_hash = Hash.new(0)
    # traverse all the shift numbers to find the optimal number
    all_possible_shifts = (1..25).to_a
    all_possible_shifts.each do |shift|
      # record the occurrency of specific characters
      # 
      # Example
      #   character => frequency
      #   e => 15.68
      #   t => 9.80
      character_freq_hash = Hash.new(0)
      # record the total number of characters
      total_count = 0
      define_alphabet(shift)
      # convert an array of words to a string of decrypted string
      decrypted_string = decrypt(words.join)
      total_count = decrypted_string.length.to_f
      # convert a string to an array of chars
      decrypted_array = decrypted_string.chars.to_a
      # count the occurrency of each character
      decrypted_array.each{|character| character_freq_hash[character] += 1}
      # caculate the frequency
      character_freq_hash.each{|k, v| character_freq_hash[k] = 100 * v / total_count}
      # caculate the sum of square of the differences
      square_difference_sum = 0.0
      character_freq_hash.each do |key, value|
        defined_value = defined_character_frequency[key]
        square_difference_sum = square_difference_sum + (defined_value - value)**2
      end
      shift_sum_hash[shift] = square_difference_sum
    end
    # sort the shift_sum_hash by value and pop the smallest value
    # then get an array like this : [key, value], key is the optimal shift number
    optimal_shift = shift_sum_hash.sort_by{|_, v| v}.reverse.pop()[0]
    return optimal_shift
  end
end

# Take 3 parameters from the command line
# ARGV[0] - filename
# ARGV[1] - the file type of output
# ARGV[2] - the filename of css
# set the default value for every argument if it has no value
inputname = (ARGV[0] == nil) ? "input1.txt" : ARGV[0]
format = (ARGV[1] == nil) ? "txt" : ARGV[1]
cssfile = (ARGV[2] == nil) ? "1" : ARGV[2]
CaesarCipher.new(inputname,format,cssfile)