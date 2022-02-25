const redundant = require('./redundant');

test('f1(apple)() -> apple', () => {
    expect(redundant('apple')()).toBe('apple');
});

test('f2(pear)() -> pear', () => {
    expect(redundant('pear')()).toBe('pear');
});

test('f3()() -> ""', () => {
    expect(redundant('')()).toBe('');
});