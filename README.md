### CurrencyConverter
This simple Currency Converter app is designed to illustrate working with complex json objects as well showing usage of Flow, Live Data, Coroutines, Hilt - (Clean Architecture)

I also decided to use Room Database to store all currencies locally. This eliminates the dependency on internet everytime to fetch list of currencies.

Data is provided by [Currency Conversion API](https://currency.getgeoapi.com)

### Details

- **Operating System** : Android
- **Programming Language**: [Kotlin](https://kotlinlang.org)
- **Architecture** : [MVVM](https://developer.android.com/jetpack/guide)
- **Dependency Injection** : [Hilt](https://dagger.dev/hilt/)
- **Fragment Management** : [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)
- **Material Components for Android** : [Material Components for Android](https://github.com/material-components/material-components-android)
- **Design** : [Material Design 3](https://m3.material.io)
- **Moshi** : [Moshi](https://github.com/square/moshi)
- **Retrofit** : [Retrofit](https://square.github.io/retrofit)
- **Room** : [Room](https://developer.android.com/topic/libraries/architecture/room)
- **Coroutines** : [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- **Kotlin Flow** : [Kotlin Flow](https://developer.android.com/kotlin/flow)
- **LiveData** : [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- **ViewModel** : [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

The rates screen response data.
```json
{
   "status":"success",
   "currencies": {
      "AUD":"Australian Dollar",
      "BGN":"Bulgarian Lev"
   }
}
```

Conversion response data
```json
{
   "base_currency_code": "USD",
   "base_currency_name": "United States dollar",
   "amount": "100.0000",
   "updated_date": "2022-05-22",
   "rates": {
      "PLN": {
         "currency_name": "Polish złoty",
         "rate": "4.3826",
         "rate_for_amount": "438.2576"
      },
      "THB": {
         "currency_name": "Thai baht",
         "rate": "34.4133",
         "rate_for_amount": "3441.3273"
      }
   },
   "status": "success"
}
```

## Getting started

There are a few ways to open this project.

### Android Studio

1. `Android Studio` -> `File` -> `New` -> `From Version control` -> `Git`
2. Enter `git@github.com:mbobiosio/CurrencyConverter.git` into URL field an press `Clone` button
   3, Build the project and run on an android device or emulator

### Command-line + Android Studio

1. Run `git clone git@github.com:mbobiosio/CurrencyConverter.git` command to clone project
2. Open `Android Studio` and select `File | Open...` from the menu. Select cloned directory and press `Open` button
3. Build the project and run on an android device or emulator

You need to supply API key for conversions. You can get a free api key from `getgeoapi.com`.

When you obtain the key, you can provide them to the app by putting the following in the `local.properties` project root file:
```properties
apiKey = "insert key here"
```

## Screenshots
<img src="assets/home_screen.png" alt="Home" width="250"> <img src="assets/rates.png" alt="Exchange rates" width="250">

### Author

* [Mbuodile Obiosio](https://linktr.ee/mbobiosio/)
* [![Follow me](https://img.shields.io/twitter/follow/cazewonder?style=social)](https://twitter.com/cazewonder)

## 📝 License
This project is released under the MIT license.
See [LICENSE](./LICENSE) for details.

```
MIT License

Copyright (c) 2022 Mbuodile Obiosio

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
