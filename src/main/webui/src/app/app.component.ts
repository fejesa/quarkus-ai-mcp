import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RouterOutlet} from '@angular/router';
import {InputTextModule} from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import {EditorModule} from 'primeng/editor';
import {MessageModule} from 'primeng/message';
import {CommonModule} from '@angular/common';
import {FloatLabelModule} from 'primeng/floatlabel';
import {ProgressBarModule} from 'primeng/progressbar';
import {MessageTemplateAPIService} from "./api/service/message-template.service";

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [
        CommonModule,
        RouterOutlet,
        InputTextModule,
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
    message = '';
    loading = false;

    constructor(private messageTemplateAPIService: MessageTemplateAPIService) {
    }

    onClick() {
        this.message = 'Sent to Agent, waiting for response...';
        this.loading = true;
        this.messageTemplateAPIService.postApi(({
            content: this.templateContent,
            description: this.templateDescription
        })).subscribe({
            next: (response) => {
                this.templateContent = response;
                this.message = 'Response received from Agent.';
                this.loading = false;
            },
            error: (error) => {
                this.message = 'Error: ' + error.message;
                console.error(error);
                this.loading = false;
            }
        });
    }
}
