# Kotlin Multiplatform MTLS Example with KTOR

This is an example of how to use MTLS with Kotlin Multiplatform and ktor.

## What is MTLS?

Mutual TLS (MTLS) is a method of authentication where both the client and server present a certificate to each other.
This is in contrast to TLS where only the server presents a certificate to the client.

On Android, You'll need to create your own SSLContext and pass it to the client.
On iOS, You'll need to create a `NSURLCredentials` object and pass it to the client.

## What is this

This is just an example about how to use MTLS with Kotlin Multiplatform and ktor. It is not a library or a framework, it
is just a simple example.

For the example purposes, we use badssl.com.

## Show me the code

In order to simulate a "real world" app, we use Koin for dependency injection, and we use a simple MVVM architecture.

### Multiplatform part

As you see, this example simulates a Kotlin Multiplatform "ViewModel". It just an example, obviously make HTTP requests
is not ok.

This is how VM looks like:

### HttpClientProvider

The idea is simple, we should use different `HttpClient` for different platforms. For JVM we use `OkHttp` and for iOS we
use `Darwin`.

```kotlin
expect class HttpClientProvider {
    fun clientWithMtls(block: HttpClientConfig<*>.() -> Unit): HttpClient
    fun client(block: HttpClientConfig<*>.() -> Unit): HttpClient
}
```

### JVM

You'll need to import the dependency `io.github.hakky54:sslcontext-kickstart:7.4.9` in your shared module.

IMPORTANT: Don't follow any tutorial from Google or StackOverflow, they will not work (at least on my side)

Then, in `androidMain`:

```kotlin
actual class HttpClientProvider(keystore: InputStream, keystorePassword: String) {

    // This is the trustManager for server certificate
    private val trustManager: X509TrustManager

    // This is the keyManager for client certificate
    private val sslContext: SSLContext

    init {
        // Load the keystore to trust the requests
        val factory = SSLFactory.builder()
            .withIdentityMaterial(keystore, keystorePassword.toCharArray(), "PKCS12")
            .withDefaultTrustMaterial()
            .build()

        sslContext = factory.sslContext
        trustManager = factory.trustManagerFactory.get()
            .trustManagers?.first { it is X509TrustManager } as X509TrustManager
    }

    actual fun clientWithMtls(block: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
        // "inject" the trustManager and sslContext to the client
        engine {
            config {
                sslSocketFactory(
                    sslContext.socketFactory,
                    trustManager
                )
            }
        }
        // Give the caller to configure the client
        block(this)
    }

    actual fun client(block: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
        block(this)
    }
}
```

### iOS

```kotlin

actual class HttpClientProvider(private val urlCredential: NSURLCredential) {
    actual fun clientWithMtls(block: HttpClientConfig<*>.() -> Unit) = HttpClient(Darwin) {
        engine {
            handleChallenge(MyChallengerHandler(urlCredential))
        }
        block(this)
    }

    actual fun client(block: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(Darwin) {
        block(this)
    }
}

class MyChallengerHandler(private val urlCredential: NSURLCredential) : ChallengeHandler {

    override fun invoke(
        session: NSURLSession,
        task: NSURLSessionTask,
        challenge: NSURLAuthenticationChallenge,
        completionHandler: (NSURLSessionAuthChallengeDisposition, NSURLCredential?) -> Unit
    ) {
        completionHandler(NSURLSessionAuthChallengeUseCredential, urlCredential)
    }

}

```

Copy the MTLSHelper file to your iOS project and inject it

```swift
@main
struct iOSApp: App {
    
    init() {
        let mtlsHelper = MTLSHelper()
        if let credentials =  mtlsHelper.buildURLCrendentialsWithCertificate() {
            PlatformModuleKt.doInitKoinIos(urlCredential: credentials)
        }
    }
    
	var body: some Scene {
		WindowGroup {
			SampleView()
		}
	}
}

```

That's it.