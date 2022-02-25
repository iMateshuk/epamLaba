'use strict';

/**
 * https://en.wikipedia.org/wiki/Tower_of_Hanoi
 *
 * @param {number} disks
 * @return {number}
 */
function towerHanoi(disks) {
  //console.log(Math.pow(2, disks) - 1);
  var x = 0;
  stepsToSolveHanoi(disks, "A", "C", "B");
  return x;

  function stepsToSolveHanoi(disks, srcP, desP, bufferP) {
    if (disks >= 1) {

      // Move a tower of height-1 to the buffer peg, using the destination peg.
      stepsToSolveHanoi(disks - 1, srcP, bufferP, desP);

      // Move the remaining disk to the destination peg.
      //console.log('Move disk from Tower ', srcP, ' to Tower ', desP);

      // Move the tower of `height-1` from the `buffer peg` to the `destination peg` using the `source peg`.        
      stepsToSolveHanoi(disks - 1, bufferP, desP, srcP);

      x++;
    }
    return;
  }
}
module.exports = towerHanoi;