'use strict';

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
module.exports = matrixMultiplication;