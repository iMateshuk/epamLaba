const secondsToDate = require('./secondsToDate');

test('31536000 -> 01.06.2021', () => {
  expect(secondsToDate(31536000)).toBe('01.06.2021');
});

test('0 -> 01.06.2020', () => {
    expect(secondsToDate(0)).toBe('01.06.2020');
});

test('86400 -> 02.06.2020', () => {
    expect(secondsToDate(86400)).toBe('02.06.2020');
});