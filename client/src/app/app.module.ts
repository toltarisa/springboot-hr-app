import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { SuiModule } from "ng2-semantic-ui";

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, AppRoutingModule, SuiModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
