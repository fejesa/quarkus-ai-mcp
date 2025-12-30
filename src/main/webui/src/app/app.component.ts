import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RouterOutlet} from '@angular/router';
import {ButtonModule} from 'primeng/button';
import {EditorModule} from 'primeng/editor';
import {MessageModule} from 'primeng/message';
import {TextareaModule} from "primeng/textarea";

import {FloatLabelModule} from 'primeng/floatlabel';
import {ProgressBarModule} from 'primeng/progressbar';
import {MessageTemplateAPIService} from "./api/service/message-template.service";

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [
    RouterOutlet,
    TextareaModule,
    ButtonModule,
    EditorModule,
    MessageModule,
    FormsModule,
    FloatLabelModule,
    ProgressBarModule
],
    providers: [
        MessageTemplateAPIService
    ],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss',
})
export class AppComponent {
    templateDescription = '';
    templateContent = '';
    msg = '';
    loading = false;

    constructor(private messageTemplateAPIService: MessageTemplateAPIService) {
    }

    onClick() {
        this.msg = 'Sent to Agent, waiting for response...';
        this.loading = true;
        this.messageTemplateAPIService.postApi(({
            content: this.templateContent,
            description: this.templateDescription
        })).subscribe({
            next: (response) => {
                this.templateContent = response;
                this.msg = 'Response received from Agent.';
                this.loading = false;
            },
            error: (error) => {
                this.msg = 'Error: ' + error.message;
                console.error(error);
                this.loading = false;
            }
        });
    }
}
