import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RouterOutlet} from '@angular/router';
import {InputTextModule} from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import {EditorModule} from 'primeng/editor';
import {MessageModule} from 'primeng/message';
import {CommonModule} from '@angular/common';
import {FloatLabelModule} from 'primeng/floatlabel';
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
        FloatLabelModule
    ],
    providers: [
        MessageTemplateAPIService
    ],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss',
})
export class AppComponent {
    templateDescription = '';

    msg = '';

    templateContent = '';

    constructor(private messageTemplateAPIService: MessageTemplateAPIService) {
    }

    onClick() {
        this.msg = 'Sent to LLM';
        this.messageTemplateAPIService.postApi(({
            templateContent: this.templateContent,
            description: this.templateDescription
        })).subscribe({
            next: (response) => {
                this.templateContent = response;
            },
            error: (error) => {
                this.msg = 'Error: ' + error.message;
                console.error(error);
            }
        });
    }
}
