'use strict';

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
module.exports = repeatingLitters;