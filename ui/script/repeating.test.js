const repeating = require('./repeating');

test('Hello -> HHeelloo', () => {
  expect(repeating('Hello')).toBe('HHeelloo');
});

test('Hello world -> HHeello  wworrldd', () => {
    expect(repeating('Hello world')).toBe('HHeello  wworrldd');
});