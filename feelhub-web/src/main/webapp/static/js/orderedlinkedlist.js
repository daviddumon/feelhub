/* Copyright Feelhub 2012 */
function OrderedLinkedList(sortableProperty) {

    this.add = function(element) {
        var node = {
            data: element,
            nextNode: null,
            previousNode: null
        };

        if(this.length == 0) {
            this.currentNode = node;
            this.startNode = node;
            this.endNode = node;
        } else if(this.length == 1){
            if(this.currentNode.data[this.sortableProperty].isBefore(element[this.sortableProperty])) {
                node.previousNode = this.currentNode;
                this.currentNode.nextNode = node;
                this.endNode = node;
            } else {
                node.nextNode = this.currentNode;
                this.currentNode.previousNode = node;
                this.startNode = node;
            }
        } else {
            if(this.startNode.data[this.sortableProperty].isAfter(element[this.sortableProperty])){
                this.startNode.previousNode = node;
                node.nextNode = this.startNode;
                this.startNode = node;
            } else {
                var previousNode = this.startNode;
                while(previousNode.nextNode != null && previousNode.nextNode.data[this.sortableProperty].isBefore(element[this.sortableProperty])) {
                    previousNode = previousNode.nextNode;
                }

                node.previousNode = previousNode;
                if(previousNode.nextNode != null) {
                    node.nextNode = previousNode.nextNode;
                    previousNode.nextNode.previousNode = node;
                }
                previousNode.nextNode = node;

                if(node.nextNode == null) {
                    this.endNode = node;
                }
            }
        }

        this.length++;
    };

    this.find = function(propertyValue) {
        if(this.sortableProperty != null) {
            var node = this.startNode;
            while(node != null){
                if(node.data[this.sortableProperty].equals(propertyValue)) {
                    return node.data;
                }
                node = node.nextNode;
            }
        }
        return null;
    };

    this.setCurrent = function(element) {
        var found = false;
        var node = this.startNode;
        while(node != null && !found){
            if(node.data.equals(element)) {
                this.currentNode = node;
                found = true;
            }
            node = node.nextNode;
        }
    };

    this.current = function() {
        return (this.currentNode != null ? this.currentNode.data : null);
    };

    this.next = function() {
        return (this.currentNode.nextNode != null ? this.currentNode.nextNode.data : null);
    };

    this.previous = function() {
        return (this.currentNode.previousNode != null ? this.currentNode.previousNode.data : null);
    };

    this.setPreviousAsCurrentNode = function() {
        this.currentNode = this.currentNode.previousNode;
    };

    this.setNextAsCurrentNode = function() {
        this.currentNode = this.currentNode.nextNode;
    };
    
    this.length = 0;
    this.currentNode = null;
    this.startNode = null;
    this.endNode = null;
    this.sortableProperty = (sortableProperty !== undefined ? sortableProperty : null);
}