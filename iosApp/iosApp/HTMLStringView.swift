//
//  HTMLStringView.swift
//  iosApp
//
//  Created by Sergio Casero Hernández on 27/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import WebKit
import SwiftUI

struct HTMLStringView: UIViewRepresentable {
    let htmlContent: String

    func makeUIView(context: Context) -> WKWebView {
        return WKWebView()
    }

    func updateUIView(_ uiView: WKWebView, context: Context) {
        uiView.loadHTMLString(htmlContent, baseURL: nil)
    }
}
