const matrix = require('./matrix');

test('matrix1, matrix2 -> matrix3', () => {
   let a = [
      [1, 2, 3, 1],
      [4, 5, 6, 1],
      [7, 8, 9, 1],
      [1, 1, 1, 1],
      [5, 7, 2, 6]
   ];
   let b = [
      [1, 4, 7, 3, 4, 6],
      [2, 5, 8, 7, 3, 2],
      [3, 6, 9, 6, 7, 8],
      [1, 1, 1, 2, 3, 6]
   ];
   let c = [
      [15, 33, 51, 37, 34, 40],
      [33, 78, 123, 85, 76, 88],
      [51, 123, 195, 133, 118, 136],
      [7, 16, 25, 18, 17, 22],
      [31, 73, 115, 88, 73, 96]
   ];
   expect(matrix(a, b)).toStrictEqual(c);
});

test('matrixA[[1,2], [3,2]], matrixB[[3,2],[1,1]] -> matrixC[[5,4],[11,8]]', () => {
   let a = [
      [1, 2],
      [3, 2]
   ];
   let b = [
      [3, 2],
      [1, 1]
   ];
   let c = [
      [5, 4],
      [11, 8]
   ];
   expect(matrix(a, b)).toStrictEqual(c);
});