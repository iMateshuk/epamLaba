const gather = require('./gather');

test('gather("a")("b")("c").order(0)(1)(2).get() -> "abc"', () => {
    expect(gather("a")("b")("c").order(0)(1)(2).get()).toBe('abc');
});

test('gather("a")("b")("c").order(2)(1)(0).get() -> "cba"', () => {
    expect(gather("a")("b")("c").order(2)(1)(0).get()).toBe('cba');
});

test('gather("e")("l")("o")("l")("!")("h").order(5)(0)(1)(3)(2)(4).get() -> "hello!"', () => {
    expect(gather("e")("l")("o")("l")("!")("h").order(5)(0)(1)(3)(2)(4).get()).toBe('hello!');
});

test('gather("e")("l")("o")("l")("!")("h").order(5)(0)(1)(3)(2).get() -> "hello"', () => {
    expect(gather("e")("l")("o")("l")("!")("h").order(5)(0)(1)(3)(2).get()).toBe('hello');
});