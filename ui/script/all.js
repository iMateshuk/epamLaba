'use strict';

/**
 * You must return a date that comes in a predetermined number of seconds after 01.06.2020 00:00:002020
 * @param {number} seconds
 * @returns {Date}
 *
 * @example
 *      31536000 -> 01.06.2021
 *      0 -> 01.06.2020
 *      86400 -> 02.06.2020
 */
function secondsToDate(seconds) {
    var t = new Date(2020, 6, 1);
    t.setSeconds(seconds);
    var dd = String(t.getDate()).padStart(2, '0');
    var mm = String(t.getMonth()).padStart(2, '0');
    var yyyy = t.getFullYear();

    return dd + '.' + mm + '.' + yyyy;
}

/**
 * You must create a function that returns a base 2 (binary) representation of a base 10 (decimal) string number
 * ! Numbers will always be below 1024 (not including 1024)
 * ! You are not able to use parseInt
 * @param {number} decimal
 * @return {string}
 *
 * @example
 *      5 -> "101"
 *      10 -> "1010"
 */
function toBase2Converter(decimal) {
    if (decimal >= 1024) decimal = 1023;
    return Number(decimal).toString(2);
}

/**
 * You must create a function that takes two strings as arguments and returns the number of times the first string
 * is found in the text.
 * @param {string} substring
 * @param {string} text
 * @return {number}
 *
 * @example
 *      'a', 'test it' -> 0
 *      't', 'test it' -> 2
 *      'T', 'test it' -> 2
 */
function substringOccurrencesCounter(substring, text) {
    return text.toLowerCase().split(substring.toLowerCase()).length - 1;
}

/**
 * You must create a function that takes a string and returns a string in which each character is repeated once.
 *
 * @param {string} string
 * @return {string}
 *
 * @example
 *      "Hello" -> "HHeelloo"
 *      "Hello world" -> "HHeello  wworrldd" // o, l is repeated more then once. Space was also repeated
 */
function repeatingLitters(string) {
    var n = 2;
    let res = '';
    for(let i = 0; i < string.length; i++){
        if ((string.split(string[i]).length - 1) > 1) {
            res += string[i];
        } else {
           res += string[i].repeat(n);
        };
    };
    return res;
}

/**
 * You must write a function redundant that takes in a string str and returns a function that returns str.
 * ! Your function should return a function, not a string.
 *
 * @param {string} str
 * @return {function}
 *
 * @example
 *      const f1 = redundant("apple")
 *      f1() ➞ "apple"
 *
 *      const f2 = redundant("pear")
 *      f2() ➞ "pear"
 *
 *      const f3 = redundant("")
 *      f3() ➞ ""
 */
function redundant(str) {
    return () => {return str};
}

/**
 * https://en.wikipedia.org/wiki/Tower_of_Hanoi
 *
 * @param {number} disks
 * @return {number}
 */
function towerHanoi(disks) {

    var x=0;
    stepsToSolveHanoi(disks, "A", "C", "B");
    return x;
    
    function stepsToSolveHanoi(disks, srcP, desP, bufferP) {
        if (disks >= 1) {
      
            stepsToSolveHanoi(disks - 1, srcP, bufferP, desP);
      
          //console.log('Move disk from Tower ', srcP, ' to Tower ', desP);
      
            stepsToSolveHanoi(disks - 1, bufferP, desP, srcP);

            x++;
        }
        return;
    }
}

/**
 * You must create a function that multiplies two matricies (n x n each).
 *
 * @param {array} matrix1
 * @param {array} matrix2
 * @return {array}
 *
 */
function matrixMultiplication(matrix1, matrix2) {
    let result = [];

    for (let X = 0; X < matrix1.length; X++) {
  
        let newRow = [];
        for (let Y = 0; Y < matrix2[0].length; Y++) {
          
            let newDotProd = 0;
            for (let i = 0; i < matrix1[X].length; i++) {
                newDotProd += matrix1[X][i] * matrix2[i][Y];
            }
            newRow.push(newDotProd);
        }
        result.push(newRow);
    }
    return result;
}

/**
 * Create a gather function that accepts a string argument and returns another function.
 * The function calls should support continued chaining until order is called.
 * order should accept a number as an argument and return another function.
 * The function calls should support continued chaining until get is called.
 * get should return all of the arguments provided to the gather functions as a string in the order specified in the order functions.
 *
 * @param {string} str
 * @return {string}
 *
 * @example
 *      gather("a")("b")("c").order(0)(1)(2).get() ➞ "abc"
 *      gather("a")("b")("c").order(2)(1)(0).get() ➞ "cba"
 *      gather("e")("l")("o")("l")("!")("h").order(5)(0)(1)(3)(2)(4).get()  ➞ "hello"
 */
 gather.words = [];
 function gather(word) {
     gather.words.push(word);
     return gather;
 }
 
 gather.nums = [];
 gather.order = function(num) {
     gather.nums.push(num);
     return gather.order;
 }
 
 gather.order.get = function() {
 
     var string = gather.nums
         .map(num => gather.words[num])
         .join('');
     gather.nums = [];
     gather.words = [];
     
     return string;
 }