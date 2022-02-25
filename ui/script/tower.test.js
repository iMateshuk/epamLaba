const tower = require('./tower');

test('3 -> 2^n - 1 (7)', () => {
    expect(tower(3)).toBe(7);
});

test('8 -> 2^n - 1 (255)', () => {
    expect(tower(8)).toBe(255);
});