import SwiftUI
import shared
import SwiftUI


struct SampleView: View {
    
    @StateObject var observable = SampleObservable()

	var body: some View {
        
                
        VStack {
            Text("Welcome to the MTLS KMM test app")
            
            if observable.state is SampleViewState.Idle {
                Text("Tap on the buttons to start testing")
            } else if observable.state is SampleViewState.Loading {
                ProgressView()
            } else if observable.state is SampleViewState.Error {
                let state = observable.state as! SampleViewState.Error
                HTMLStringView(htmlContent: state.error)
            } else if observable.state is SampleViewState.Success {
                let state = observable.state as! SampleViewState.Success
                HTMLStringView(htmlContent: state.data)
                    .background(Color.green)
            }
            
            HStack {
                Button("With MTLS") {
                    observable.viewModel.onAction(action: SampleActions.OnLoadWithMtls())
                }
                Button("Without MTLS") {
                    observable.viewModel.onAction(action: SampleActions.OnLoadWithoutMtls())
                }
            }
        }       
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		SampleView()
	}
}
