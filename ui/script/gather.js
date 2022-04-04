'use strict';

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
 *      gather("e")("l")("o")("l")("!")("h").order(5)(0)(1)(3)(2)(4).get() ➞ "hello"
 */
gather.words = [];
function gather(str) {
    gather.words.push(str);
    return gather;
}

gather.nums = [];
gather.order = function (num) {
    gather.nums.push(num);
    return gather.order;
}

gather.order.get = function () {

    var string = gather.nums
        .map(num => gather.words[num])
        .join('');

    gather.nums = [];
    gather.words = [];

    return string;
}
module.exports = gather;