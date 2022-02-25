const substring = require('./substring');

test('a, test it -> 0', () => {
  expect(substring('a', 'test it')).toBe(0);
});

test('t, test it -> 3', () => {
    expect(substring('t', 'test it')).toBe(3);
});

test('T, test it -> 3', () => {
    expect(substring('T', 'test it')).toBe(3);
});