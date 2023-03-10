import SwiftUI
import shared

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
