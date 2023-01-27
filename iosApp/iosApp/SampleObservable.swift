//
//  SampleObservable.swift
//  iosApp
//
//  Created by Sergio Casero Hernández on 27/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

class SampleObservable: ObservableObject {
    
    var viewModel: SampleViewModel
    
    @Published var state: SampleViewState = SampleViewState.Idle()
    
    init() {
        viewModel = SampleViewModel(initialState: SampleViewState.Idle())
        
        viewModel.onEach { newVal in
            guard let newState = newVal else {
                return
            }
            self.state = newState
        }
    }
    
}

