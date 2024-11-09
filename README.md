# react-native-display-size

Access additional display metrics on Android devices: status bar height, soft menu bar height, real screen size.
Based on the react-native-extra-dimensions-android project.
Support New Architectures

## Installation

```sh
npm install react-native-display-size
```

## Usage

```js


const realWindowHeight: number = getRealWindowHeight();
const realWindowWidth: number = getRealWindowWidth();
const softMenuBarHeight: number = getSoftMenuBarHeight();
const statusBarHeight: number = getStatusBarHeight();
const smartBarHeight: number = getSmartBarHeight();
const softMenuBarEnabled: number = getSoftMenuBarEnabled();


```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
