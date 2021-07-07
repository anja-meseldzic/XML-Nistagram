import { ClicksByProfile } from "./clicks-by-profile";
import { PostReport } from "./post-report";

export class Report {
    constructor(
        public postReport : PostReport,
        public clicks : ClicksByProfile[],
        public totalClicks : number,
        public timesPublished : number
    ) { }
}